package com.sneakerspot.model;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int sneakerId;
    private int buyerId;
    private int sellerId;
    private int quantity;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;

    private User buyer;
    private User seller;

    public Order() {
    }

    public Order(int id, int sneakerId, int buyerId, int sellerId, int quantity, double totalPrice,
                 LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.sneakerId = sneakerId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSneakerId() { return sneakerId; }
    public void setSneakerId(int sneakerId) { this.sneakerId = sneakerId; }

    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
}