package com.sneakerspot.model;

public class Order {
    private int id;
    private int sneakerId;
    private int buyerId;
    private int sellerId;
    private int quantity;
    private double totalPrice;
    private String date;

    public Order() {
    }

    public Order(int id, int sneakerId, int buyerId, int sellerId, int quantity, double totalPrice, String date) {
        this.id = id;
        this.sneakerId = sneakerId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSneakerId() {
        return sneakerId;
    }

    public void setSneakerId(int sneakerId) {
        this.sneakerId = sneakerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}