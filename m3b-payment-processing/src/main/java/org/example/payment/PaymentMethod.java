package org.example.payment;

import org.example.model.PaymentResult;

public abstract class PaymentMethod {
    private final String providerName;
    protected PaymentMethod(String providerName){
        this.providerName = providerName;
    }
    public PaymentResult pay(double amount){
        if (amount <= 0){
            return new  PaymentResult(false, "Amount must be greater than zero.");
        }
        return processPayment(amount);
    }

    protected abstract PaymentResult processPayment(double amount);
    public String getProviderName() {
        return providerName;
    }
}
