const api = {
    list: () => fetch('/api/chats').then(r => r.json()),
    create: () => fetch('/api/chats', { method: 'POST' }).then(r => r.json()),
    get: (id) => fetch(`/api/chats/${id}`).then(r => r.json()),
    remove: (id) => fetch(`/api/chats/${id}`, { method: 'DELETE' }),
    send: (id, content) => fetch(`/api/chats/${id}/chatMessages`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ content })
    }).then(async r => {
        if (!r.ok) {
            const problem = await r.json().catch(() => ({}));
            throw new Error(problem.detail || 'Request failed');
        }
        return r.json();
    })
};

function escapeHtml(s) {
    return String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function inlineMd(s) {
    return escapeHtml(s)
        .replace(/`([^`]+)`/g, '<code>$1</code>')
        .replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>');
}

function renderRich(el, text) {
    el.innerHTML = '';
    const segments = String(text).split('```');
    segments.forEach((seg, i) => {
        if (i % 2 === 1) {
            const nl = seg.indexOf('\n');
            const code = nl >= 0 ? seg.slice(nl + 1) : seg;
            const pre = document.createElement('pre');
            pre.className = 'code-block';
            pre.textContent = code.replace(/\s+$/, '');
            el.appendChild(pre);
        } else if (seg.length) {
            const span = document.createElement('span');
            span.className = 'md';
            span.innerHTML = inlineMd(seg);
            el.appendChild(span);
        }
    });
}

function typingHtml() {
    return '<span class="typing"><span></span><span></span><span></span></span>';
}

const els = {
    chatList: document.getElementById('chat-list'),
    messages: document.getElementById('chatMessages'),
    title: document.getElementById('chat-title'),
    input: document.getElementById('input'),
    send: document.getElementById('send'),
    form: document.getElementById('composer'),
    error: document.getElementById('error'),
    newChat: document.getElementById('new-chat')
};

let activeChatId = null;

function setError(message) {
    els.error.textContent = message || '';
}

function setComposerEnabled(enabled) {
    els.input.disabled = !enabled;
    els.send.disabled = !enabled;
}

async function refreshChatList() {
    const chats = await api.list();
    els.chatList.innerHTML = '';
    chats.forEach(chat => els.chatList.appendChild(renderChatItem(chat)));
}

function renderChatItem(chat) {
    const item = document.createElement('div');
    item.className = 'chat-item' + (chat.id === activeChatId ? ' active' : '');
    item.onclick = () => openChat(chat.id);

    const title = document.createElement('span');
    title.className = 'title';
    title.textContent = chat.title;

    const count = document.createElement('span');
    count.className = 'count';
    count.textContent = chat.messageCount;

    const del = document.createElement('button');
    del.className = 'del';
    del.textContent = '🗑';
    del.onclick = (e) => { e.stopPropagation(); deleteChat(chat.id); };

    item.append(title, count, del);
    return item;
}

function renderMessages(chat) {
    els.title.textContent = chat.title;
    els.messages.innerHTML = '';
    if (!chat.chatMessages.length) {
        els.messages.innerHTML = '<div class="empty">Say hello to start the conversation.</div>';
        return;
    }
    chat.chatMessages.forEach(m => els.messages.appendChild(renderMessage(m)));
    els.messages.scrollTop = els.messages.scrollHeight;
}

function renderMessage(message) {
    const wrapper = document.createElement('div');
    wrapper.className = `msg ${message.role.toLowerCase()}`;
    const role = document.createElement('div');
    role.className = 'role';
    role.textContent = message.role;
    const body = document.createElement('div');
    if (message.role.toLowerCase() === 'assistant') {
        renderRich(body, message.content);
    } else {
        body.className = 'md';
        body.textContent = message.content;
    }
    wrapper.append(role, body);
    return wrapper;
}

async function openChat(id) {
    setError('');
    activeChatId = id;
    const chat = await api.get(id);
    renderMessages(chat);
    setComposerEnabled(true);
    els.input.focus();
    await refreshChatList();
}

async function startNewChat() {
    setError('');
    const chat = await api.create();
    await refreshChatList();
    await openChat(chat.id);
}

async function deleteChat(id) {
    setError('');
    await api.remove(id);
    if (id === activeChatId) {
        activeChatId = null;
        els.title.textContent = 'Select or start a chat';
        els.messages.innerHTML = '<div class="empty">No conversation selected.</div>';
        setComposerEnabled(false);
    }
    await refreshChatList();
}

async function submitMessage(content) {
    setError('');
    setComposerEnabled(false);
    appendPending(content);
    try {
        const chat = await api.send(activeChatId, content);
        renderMessages(chat);
        await refreshChatList();
    } catch (err) {
        setError(err.message);
        const chat = await api.get(activeChatId);
        renderMessages(chat);
    } finally {
        setComposerEnabled(true);
        els.input.focus();
    }
}

function appendPending(content) {
    els.messages.querySelector('.empty')?.remove();
    els.messages.appendChild(renderMessage({ role: 'USER', content }));

    const thinking = document.createElement('div');
    thinking.className = 'msg assistant';
    const role = document.createElement('div');
    role.className = 'role';
    role.textContent = 'ASSISTANT';
    const body = document.createElement('div');
    body.innerHTML = typingHtml();
    thinking.append(role, body);

    els.messages.appendChild(thinking);
    els.messages.scrollTop = els.messages.scrollHeight;
}

els.newChat.onclick = startNewChat;

els.form.addEventListener('submit', (e) => {
    e.preventDefault();
    const content = els.input.value.trim();
    if (!content || !activeChatId) return;
    els.input.value = '';
    submitMessage(content);
});

els.input.addEventListener('keydown', (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        els.form.requestSubmit();
    }
});

refreshChatList();

const tabs = document.querySelectorAll('nav.tabs button');
const views = {
    chat: document.getElementById('view-chat'),
    code: document.getElementById('view-code'),
    assistant: document.getElementById('view-assistant'),
    agents: document.getElementById('view-agents')
};
tabs.forEach(btn => btn.addEventListener('click', () => {
    tabs.forEach(b => b.classList.toggle('active', b === btn));
    Object.entries(views).forEach(([name, el]) => el.classList.toggle('active', name === btn.dataset.view));
}));

async function postJson(url, payload) {
    const r = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!r.ok) {
        const problem = await r.json().catch(() => ({}));
        throw new Error(problem.detail || 'Request failed');
    }
    return r.json();
}

function addChips(container, prompts, onPick) {
    prompts.forEach(text => {
        const chip = document.createElement('button');
        chip.className = 'chip';
        chip.textContent = text;
        chip.onclick = () => onPick(text);
        container.appendChild(chip);
    });
}

const assistant = {
    input: document.getElementById('assistant-input'),
    send: document.getElementById('assistant-send'),
    err: document.getElementById('assistant-err'),
    answer: document.getElementById('assistant-answer'),
    chips: document.getElementById('assistant-chips')
};

async function askAssistant(question) {
    assistant.err.textContent = '';
    assistant.send.disabled = true;
    assistant.answer.className = 'card';
    assistant.answer.innerHTML = typingHtml();
    try {
        const data = await postJson('/api/assistant', { question });
        assistant.answer.className = 'card';
        renderRich(assistant.answer, data.answer);
    } catch (e) {
        assistant.err.textContent = e.message;
        assistant.answer.className = 'card muted';
        assistant.answer.textContent = 'The answer will appear here.';
    } finally {
        assistant.send.disabled = false;
    }
}

assistant.send.onclick = () => {
    const q = assistant.input.value.trim();
    if (q) askAssistant(q);
};
assistant.input.addEventListener('keydown', (e) => {
    if (e.key === 'Enter' && !e.shiftKey) { e.preventDefault(); assistant.send.click(); }
});
addChips(assistant.chips,
    ['What is 987 multiplied by 654?', 'What is the current date and time?', 'Divide 4242 by 6.'],
    (text) => { assistant.input.value = text; askAssistant(text); });

const agents = {
    input: document.getElementById('agents-input'),
    send: document.getElementById('agents-send'),
    err: document.getElementById('agents-err'),
    ideas: document.getElementById('agents-ideas'),
    verdict: document.getElementById('agents-verdict'),
    chips: document.getElementById('agents-chips')
};

function setCard(el, label, body, opts) {
    opts = opts || {};
    el.className = 'card' + (opts.muted ? ' muted' : '');
    el.innerHTML = '';
    const l = document.createElement('div');
    l.className = 'card-label';
    l.textContent = label;
    const b = document.createElement('div');
    if (opts.typing) {
        b.innerHTML = typingHtml();
    } else if (opts.rich) {
        renderRich(b, body);
    } else {
        b.textContent = body;
    }
    el.append(l, b);
}

function resetAgentCards() {
    setCard(agents.ideas, '💡 Ideas · Agent 1', 'Ideas will appear here.', { muted: true });
    setCard(agents.verdict, '🏆 Verdict · Agent 2', "The critic's pick will appear here.", { muted: true });
}

async function runAgents(topic) {
    agents.err.textContent = '';
    agents.send.disabled = true;
    setCard(agents.ideas, '💡 Ideas · Agent 1', '', { typing: true });
    setCard(agents.verdict, '🏆 Verdict · Agent 2', 'Waiting for the critic…', { muted: true });
    try {
        const data = await postJson('/api/agents/best-idea', { topic });
        setCard(agents.ideas, '💡 Ideas · Agent 1', data.ideas, { rich: true });
        setCard(agents.verdict, '🏆 Verdict · Agent 2', data.verdict, { rich: true });
    } catch (e) {
        agents.err.textContent = e.message;
        resetAgentCards();
    } finally {
        agents.send.disabled = false;
    }
}

agents.send.onclick = () => {
    const t = agents.input.value.trim();
    if (t) runAgents(t);
};
agents.input.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') { e.preventDefault(); agents.send.click(); }
});
addChips(agents.chips,
    ['a fun weekend project for a Java developer', 'a name for a coffee shop', 'ways to learn Spring Boot faster'],
    (text) => { agents.input.value = text; runAgents(text); });

resetAgentCards();

const code = {
    input: document.getElementById('code-input'),
    send: document.getElementById('code-send'),
    err: document.getElementById('code-err'),
    result: document.getElementById('code-result'),
    modes: document.getElementById('code-modes')
};
let codeMode = 'review';

code.modes.querySelectorAll('.mode').forEach(btn => btn.addEventListener('click', () => {
    code.modes.querySelectorAll('.mode').forEach(b => b.classList.toggle('active', b === btn));
    codeMode = btn.dataset.mode;
}));

async function runCode() {
    const source = code.input.value.trim();
    if (!source) return;
    code.err.textContent = '';
    code.send.disabled = true;
    code.result.className = 'card';
    code.result.innerHTML = typingHtml();
    try {
        const data = await postJson('/api/code', { code: source, mode: codeMode });
        code.result.className = 'card';
        renderRich(code.result, data.result);
    } catch (e) {
        code.err.textContent = e.message;
        code.result.className = 'card muted';
        code.result.textContent = 'The result will appear here.';
    } finally {
        code.send.disabled = false;
    }
}

code.send.onclick = runCode;
