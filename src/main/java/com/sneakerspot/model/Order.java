package com.sneakerspot.model;

import java.time.LocalDateTime;

/**
 * Clasa pentru comenzile plasate de cumpărători
 */

public class Order {
    private Buyer buyer;
    private Product product;
    private int quantity;
    private LocalDateTime orderDate;
    private OrderStatus status;

    //Constructor
    public Order(Buyer buyer, Product product, int quantity) {
        this.buyer = buyer;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // Setter pentru status
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}