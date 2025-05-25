package com.sneakerspot.model;

import java.math.BigDecimal;

public class Product {
    private String name;
    private BigDecimal price;
    private int stock;
    private String description;

    public Product(String name, BigDecimal price, int stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Stoc insuficient");
        }
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }
}