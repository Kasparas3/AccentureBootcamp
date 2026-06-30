package org.example.model;

public class OrderItem {
    private final String name;
    private final double price;
    private final int quantity;

    public OrderItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double calculateTotal(){
        return price * quantity;
    }

    @Override
    public String toString(){
        return name + " x" + quantity + " = " + calculateTotal();
    }
}
