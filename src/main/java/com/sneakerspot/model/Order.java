package com.sneakerspot.model;

import java.time.LocalDateTime;

/**
 * Clasa pentru comenzile plasate de cumpărători
 */

public class Order {
    private int id;
    private Sneaker sneaker;
    private Buyer buyer;
    private Seller seller;
    private int quantity;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;

    //Constructor
    public Order() {}
    public Order(int id, Sneaker sneaker, Buyer buyer, Seller seller, int quantity, double totalPrice, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.sneaker = sneaker;
        this.buyer = buyer;
        this.seller = seller;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public Sneaker getSneaker() { return sneaker; }
    public Buyer getBuyer() { return buyer; }
    public Seller getSeller() { return seller; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setSneaker(Sneaker sneaker) { this.sneaker = sneaker; }
    public void setBuyer(Buyer buyer) { this.buyer = buyer; }
    public void setSeller(Seller seller) { this.seller = seller; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public void setStatus(OrderStatus status) { this.status = status;}


    }