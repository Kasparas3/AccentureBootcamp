package com.accenture.springai_bootcamp_demo.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    private static final Map<String, String> MODES = Map.of(
            "review", "You are a senior code reviewer. Review the code for bugs, edge cases, readability and "
                    + "best practices. Answer with a short bulleted list of concrete findings, most important "
                    + "first. If the code looks solid, say so briefly.",
            "explain", "You are a patient programming teacher. Explain what the code does in plain language, "
                    + "step by step, then point out anything surprising or non-obvious. Keep it concise.",
            "refactor", "You are an expert software engineer. Rewrite the code to be cleaner and more idiomatic "
                    + "without changing its behaviour. Return the refactored code in one code block, then a short "
                    + "bulleted list of what you changed and why.",
            "bugs", "You are a debugger. Identify bugs or likely runtime errors in the code. For each one give the "
                    + "offending snippet, what goes wrong, and the fix. If you find none, say so clearly.");

    private final ChatClient chatClient;

    public CodeController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping
    public CodeResponse analyze(@RequestBody CodeRequest request) {
        String code = request.code() == null ? "" : request.code().trim();
        if (code.isEmpty()) {
            return new CodeResponse("Paste some code first.");
        }

        String mode = request.mode() == null ? "review" : request.mode().toLowerCase();
        String system = MODES.getOrDefault(mode, MODES.get("review"));

        String result = chatClient.prompt()
                .system(system)
                .user("```\n" + code + "\n```")
                .call()
                .content();

        return new CodeResponse(result);
    }

    public record CodeRequest(String code, String mode) {
    }

    public record CodeResponse(String result) {
    }
}
