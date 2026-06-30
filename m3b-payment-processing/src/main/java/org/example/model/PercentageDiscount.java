package org.example.model;

public class PercentageDiscount extends Discount{
    private final double percentage;
    public PercentageDiscount(String code,double percentage){
        super(code);
        this.percentage = percentage;
    }

    @Override
    public double apply(double originalAmount) {
        return originalAmount - (originalAmount * percentage / 100);
    }
}
