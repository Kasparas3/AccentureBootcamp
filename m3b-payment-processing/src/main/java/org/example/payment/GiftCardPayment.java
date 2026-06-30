package org.example.payment;

import org.example.model.PaymentResult;

public class GiftCardPayment extends PaymentMethod{
    private final String code;
    private double balance;

    public GiftCardPayment(String code, double balance) {
        super("GiftCard");
        this.code = code;
        this.balance = balance;
    }

    @Override
    public PaymentResult processPayment(double amount){
        if(code == null || code.isBlank()){
            return new PaymentResult(false, "Invalid code");
        }
        if (amount > balance){
            return new PaymentResult(false, "GiftCard Not Enough");
        }
        balance -= amount;
        return new PaymentResult(true, "Paid " + amount + " using gift card " + code);
    }
}
