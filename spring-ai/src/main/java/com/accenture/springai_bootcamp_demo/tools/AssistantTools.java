package com.accenture.springai_bootcamp_demo.tools;

import java.time.LocalDateTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class AssistantTools {

    @Tool(description = "Return the current date and time")
    public String currentDateTime() {
        return LocalDateTime.now().toString();
    }

    @Tool(description = "Add two numbers and return the exact sum")
    public double add(double a, double b) {
        return a + b;
    }

    @Tool(description = "Subtract b from a and return the exact difference")
    public double subtract(double a, double b) {
        return a - b;
    }

    @Tool(description = "Multiply two numbers and return the exact product")
    public double multiply(double a, double b) {
        return a * b;
    }

    @Tool(description = "Divide a by b and return the exact quotient")
    public double divide(double a, double b) {
        return b == 0 ? Double.NaN : a / b;
    }
}
