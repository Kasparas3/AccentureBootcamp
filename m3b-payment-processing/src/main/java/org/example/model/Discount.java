package org.example.model;

public abstract class Discount {
    private final String code;
    protected Discount(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public abstract double apply(double originalAmount);
}
