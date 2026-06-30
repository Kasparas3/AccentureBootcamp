package org.example.payment;

import org.example.model.PaymentResult;

public class GiftCardPayment extends PaymentMethod{
    private final String code;
    private double balance;

    public GiftCardPayment(String code, double balance) {
        // TODO: contructor
    }

    @Override
    public PaymentResult processPayment(double amount){
        // TODO: check if balance is enough
        return new PaymentResult(true, "Paid " + amount + " using gift card");
    }
}
