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

    @PostMapping
    public AssistantResponse ask(@RequestBody AskRequest request) {
        String answer = chatClient.prompt()
                .system("You are a helpful assistant with tools. You are unreliable at mental arithmetic and "
                        + "do not know the real current time. For ANY arithmetic you MUST call the calculate tool "
                        + "and report its exact result. For the current date or time you MUST call the "
                        + "currentDateTime tool. Never guess these values yourself.")
                .user(request.question())
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
