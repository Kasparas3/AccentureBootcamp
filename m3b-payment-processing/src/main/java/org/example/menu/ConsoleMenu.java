package org.example.menu;

import org.example.config.AppConfig;
import org.example.model.Discount;
import org.example.model.FixedAmountDiscount;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.PaymentResult;
import org.example.model.PercentageDiscount;
import org.example.payment.PaymentMethod;
import org.example.payment.PaymentMethodFactory;
import org.example.payment.PaymentProcessor;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    private Order currentOrder;

    public void start(){
        AppConfig config = AppConfig.getInstance();
        System.out.println("Welcome to " + config.getApplicationName());

        boolean running = true;
        while(running){
            printMenu();

            int option = Integer.parseInt(scanner.nextLine());

            switch (option){
                case 1 -> createOrder();
                case 2 -> addItem();
                case 3 -> viewOrder();
                case 4 -> applyDiscount();
                case 5 -> payOrder();
                case 0 -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void createOrder(){
        System.out.println("Customer name:");
        String customerName = scanner.nextLine();

        currentOrder = Order.builder()
                .customerName(customerName)
                .build();
        System.out.println("Order created for " + customerName);
    }

    private void addItem(){
        if (currentOrder == null){
            System.out.println("No order exists. Create an order first.");
            return;
        }

        System.out.println("Item name:");
        String itemName = scanner.nextLine();

        System.out.println("Price:");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Quantity:");
        int quantity = Integer.parseInt(scanner.nextLine());

        currentOrder.addItem(new OrderItem(itemName, price, quantity));
        System.out.println("Item added to order");
    }

    private void viewOrder(){
        if (currentOrder == null){
            System.out.println("No order exists. Create an order first.");
            return;
        }

        System.out.println("Customer: " + currentOrder.getCustomerName());
        System.out.println("Status: " +  currentOrder.getStatus());
        System.out.println("Items:");

        for (OrderItem item : currentOrder.getItems()){
            System.out.println("- " + item);
        }

        double taxRate = AppConfig.getInstance().getTaxRate();
        System.out.println("Subtotal: " + currentOrder.calculateSubtotal());
        System.out.println("Discount: " + currentOrder.getDiscount().getCode());
        System.out.println("Tax: " + (taxRate * 100) + "%");
        System.out.println("Total: " + currentOrder.calculateTotal());
    }

    private void applyDiscount(){
        if (currentOrder == null){
            System.out.println("No order exists. Create an order first.");
            return;
        }

        System.out.println("""
                Discount type:
                1. Fixed amount
                2. Percentage
                """);
        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("Value:");
        double value = Double.parseDouble(scanner.nextLine());

        Discount discount = switch (type){
            case 1 -> new FixedAmountDiscount("FIXED", value);
            case 2 -> new PercentageDiscount("PERCENT", value);
            default -> throw new IllegalArgumentException("Invalid discount type");
        };

        currentOrder.applyDiscount(discount);
        System.out.println("Discount applied");
    }

    private void payOrder(){
        if (currentOrder == null){
            System.out.println("No order exists. Create an order first.");
            return;
        }

        System.out.println("""
                Select payment method:
                1. Credit Card
                2. PayPal
                3. Gift Card
                4. Crypto
                """);
        int option = Integer.parseInt(scanner.nextLine());

        PaymentMethod paymentMethod = switch(option){
            case 1 -> createCreditCardPayment();
            case 2 -> createPaypalPayment();
            case 3 -> createGiftCardPayment();
            case 4 -> createCryptoPayment();
            default -> throw new IllegalArgumentException("Invalid payment method");
        };

        PaymentResult result = paymentProcessor.process(currentOrder, paymentMethod);
        System.out.println(result.getMessage());
    }

    private PaymentMethod createCreditCardPayment(){
        System.out.println("Card number:");
        String cardNumber =  scanner.nextLine();

        System.out.println("Card holder name:");
        String cardHolderName =  scanner.nextLine();

        return PaymentMethodFactory.createCreditCardPayment(cardNumber,cardHolderName);
    }

    private  PaymentMethod createPaypalPayment(){
        System.out.println("Email:");
        String email = scanner.nextLine();

        return PaymentMethodFactory.createPaypalPayment(email);
    }

    private PaymentMethod createGiftCardPayment(){
        System.out.println("Gift card code:");
        String code = scanner.nextLine();

        System.out.println("Gift card balance:");
        double balance = Double.parseDouble(scanner.nextLine());

        return PaymentMethodFactory.createGiftCardPayment(code, balance);
    }

    private PaymentMethod createCryptoPayment(){
        System.out.println("Wallet address:");
        String walletAddress = scanner.nextLine();

        return PaymentMethodFactory.createCryptoPayment(walletAddress);
    }

    private void printMenu(){
        System.out.println("""
                1. Create order
                2. Add item to order
                3. View order
                4. Apply discount
                5. Pay order
                0. Exit
                """);
    }
}
