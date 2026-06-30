package org.example.payment;

import org.example.model.PaymentResult;

public class CryptoPayment extends PaymentMethod {
    private final String walletAddress;

    public CryptoPayment(String walletAddress) {
        super("Crypto");
        this.walletAddress = walletAddress;
    }

    @Override
    public PaymentResult processPayment(double amount){
        if (walletAddress == null || walletAddress.isBlank()){
            return new PaymentResult(false, "Invalid wallet address");
        }
        return new PaymentResult(true, "Paid " + amount + " using crypto wallet " + walletAddress);
    }
}
