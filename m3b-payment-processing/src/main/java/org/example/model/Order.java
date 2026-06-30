package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStatus status;
    private Discount discount = new NoDiscount();

    public Order(Builder builder) {
        this.customerName = builder.customerName;
        this.items = builder.items;
        this.status = OrderStatus.NEW;
    }

    public void addItem(OrderItem item){
        if (isPaid()){
            throw new IllegalStateException("Cannot add items to a paid order");
        }
        items.add(item);
    }

    public double calculateTotal(){
        double total = 0;
        for (OrderItem item : items){
            total += item.calculateTotal();
        }
        return discount.apply(total);
    }

    public void markAsPaid(){
        if (items.isEmpty()){
            throw new IllegalStateException("Cannot pay an empty order");
        }
        this.status = OrderStatus.PAID;
    }

    public void applyDiscount(Discount discount){
        this.discount = discount;
    }

    public boolean isPaid(){
        return this.status == OrderStatus.PAID;
    }

    public List<OrderItem> getItems() {
        return items;
    }
    public String getCustomerName() {
        return customerName;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private String customerName;
        private List<OrderItem> items = new ArrayList<>();
        public Builder customerName(String customerName){
            this.customerName = customerName;
            return this;
        }
        public Builder addItem(OrderItem item){
            this.items.add(item);
            return this;
        }
        public Order build(){
            if (customerName == null || customerName.isBlank()){
                throw new IllegalArgumentException("Customer name is required");
            }
            return new Order(this);
        }
    }
}
