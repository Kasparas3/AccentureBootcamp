package com.accenture.springai_bootcamp_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agents")
public class AgentWorkflowController {

    private final ChatClient chatClient;

    public AgentWorkflowController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/best-idea")
    public WorkflowResponse bestIdea(@RequestBody TopicRequest request) {
        String ideas = chatClient.prompt()
                .system("You are an idea generator. Respond with exactly three short, distinct ideas as a "
                        + "numbered list. No preamble, no explanations.")
                .user("Topic: " + request.topic())
                .call()
                .content();

        String verdict = chatClient.prompt()
                .system("You are a critic. From the given numbered ideas, choose the single best one and justify "
                        + "your choice in two sentences. Start your answer with 'Best: '.")
                .user(ideas)
                .call()
                .content();

        return new WorkflowResponse(ideas, verdict);
    }

    public record TopicRequest(String topic) {
    }

    public record WorkflowResponse(String ideas, String verdict) {
    }
}
