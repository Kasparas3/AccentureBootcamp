package org.example.payment;

import org.example.model.PaymentResult;

public class PaypalPayment extends PaymentMethod {
    private final String email;

    public PaypalPayment(String email) {
        super("PayPal");
        this.email = email;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        return new PaymentResult(true, "Paid " + amount + " using PayPal");
    }
}
