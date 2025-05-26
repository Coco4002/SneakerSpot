package com.sneakerspot.model;

import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    private List<Sneaker> sneakers;
    private List<Order> orderHistory;

    public Seller() {
        super();
        this.sneakers = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }

    public Seller(int id, String username, String email, String hashedPassword) {
        super(id, username, email, hashedPassword);
        this.sneakers = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }

    public List<Sneaker> getSneakers() { return sneakers; }
    public List<Order> getOrderHistory() { return orderHistory; }

    public void addSneaker(Sneaker sneaker) { sneakers.add(sneaker); }
    public void removeSneaker(Sneaker sneaker) { sneakers.remove(sneaker); }
    public void addOrder(Order order) { orderHistory.add(order); }
}