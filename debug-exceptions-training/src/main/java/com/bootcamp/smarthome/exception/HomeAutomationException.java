package com.bootcamp.smarthome.exception;

public class HomeAutomationException extends Exception {
    public HomeAutomationException(String message) {
        super(message);
    }

    public HomeAutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}
