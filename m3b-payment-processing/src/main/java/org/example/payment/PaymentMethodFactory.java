package org.example.payment;

public class PaymentMethodFactory {
    public static PaymentMethod createCreditCardPayment(String cardNumber,String cardHolderName){
        return new CreditCardPayment(cardNumber,cardHolderName);
    }

    // TODO: create factories for PayPal and gift card
}
