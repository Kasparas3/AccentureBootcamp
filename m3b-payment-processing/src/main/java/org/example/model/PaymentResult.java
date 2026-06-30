package org.example.model;

public class PaymentResult {
    private final boolean successful;
    private final String message;

    public PaymentResult(boolean successful, String message){
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }
    public String getMessage() {
        return message;
    }
}
