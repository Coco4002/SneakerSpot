package com.sneakerspot.model;

import java.math.BigDecimal;

/**
 * Clasă care reprezintă un produs disponibil în magazin.
 * Conține informații despre produs și gestionează stocul.
 */


public class Product {
    private String name;
    private BigDecimal price;
    private int stock;
    private String description;

    //Constructor
    public Product(String name, BigDecimal price, int stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    // Metodă pentru scăderea stocului
    public void decreaseStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Stoc insuficient");
        }
    }

    // Getters
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

    // Metodă pentru creșterea stocului
    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

}