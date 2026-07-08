package com.accenture.springai_bootcamp_demo.client;

import com.accenture.springai_bootcamp_demo.entity.ChatMessage;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Thin client over the local model, backed by Spring AI's {@link ChatClient}.
 * Callers hand over the conversation history and receive the assistant's reply.
 */
@Slf4j
@Component
public class OpenRouterClient {

    private final ChatClient chatClient;

    public OpenRouterClient(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String complete(List<ChatMessage> history) {
        String reply = call(history);
        return extractContent(reply);
    }

    private String call(List<ChatMessage> history) {
        try {
            return chatClient.prompt()
                    .messages(toMessages(history))
                    .call()
                    .content();
        } catch (OpenRouterException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            log.error("Chat model request failed", ex);
            throw new OpenRouterException("Failed to reach the chat model: " + ex.getMessage(), ex);
        }
    }

    private List<Message> toMessages(List<ChatMessage> history) {
        return history.stream().map(this::toMessage).toList();
    }

    private Message toMessage(ChatMessage message) {
        return switch (message.getRole()) {
            case SYSTEM -> new SystemMessage(message.getContent());
            case USER -> new UserMessage(message.getContent());
            case ASSISTANT -> new AssistantMessage(message.getContent());
        };
    }

    private String extractContent(String content) {
        if (!StringUtils.hasText(content)) {
            throw new OpenRouterException("The chat model returned an empty response");
        }
        return content.trim();
    }
}
