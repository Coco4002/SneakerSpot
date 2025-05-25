package com.sneakerspot.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Buyer {
    private String username;
    private String email;
    private String password;
    private List<Order> orderHistory;
    private List<PriceOffer> activeOffers;

    public Buyer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.orderHistory = new ArrayList<>();
        this.activeOffers = new ArrayList<>();
    }

    public void placeOrder(Product product, int quantity) {
        if (product.getStock() >= quantity) {
            Order order = new Order(this, product, quantity);
            orderHistory.add(order);
            product.decreaseStock(quantity);
        } else {
            throw new IllegalArgumentException("Nu există stoc suficient");
        }
    }

    public void makeOffer(Product product, BigDecimal offeredPrice) {
        PriceOffer offer = new PriceOffer(this, product, offeredPrice);
        activeOffers.add(offer);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    public List<PriceOffer> getActiveOffers() {
        return new ArrayList<>(activeOffers);
    }
}
