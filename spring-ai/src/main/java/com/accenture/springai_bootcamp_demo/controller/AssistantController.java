package com.accenture.springai_bootcamp_demo.controller;

import com.accenture.springai_bootcamp_demo.tools.AssistantTools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final ChatClient chatClient;
    private final AssistantTools assistantTools;

    public AssistantController(ChatClient.Builder chatClientBuilder, AssistantTools assistantTools) {
        this.chatClient = chatClientBuilder.build();
        this.assistantTools = assistantTools;
    }

    private static final String FALLBACK =
            "I can only help with math or the current date/time. Try something like \"What is 45 * 12?\" "
                    + "or \"What is the current time?\".";

    @PostMapping
    public AssistantResponse ask(@RequestBody AskRequest request) {
        String question = request.question() == null ? "" : request.question().trim();
        if (question.isEmpty()) {
            return new AssistantResponse(FALLBACK);
        }

        String answer = chatClient.prompt()
                .system("You are a focused assistant that only helps with arithmetic and the current date/time, "
                        + "using the provided tools. Call a tool ONLY when the user clearly asks for a calculation "
                        + "with real numbers, or for the current date/time, and report the tool's exact result. "
                        + "Never invent numbers or a calculation the user did not provide. If the message is empty, "
                        + "unclear, or not about math or time, do NOT call any tool and reply with exactly this "
                        + "sentence: " + FALLBACK)
                .user(question)
                .tools(assistantTools)
                .call()
                .content();
        return new AssistantResponse(answer);
    }

    public record AskRequest(String question) {
    }

    public record AssistantResponse(String answer) {
    }
}
