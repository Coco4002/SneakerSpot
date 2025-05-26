package com.sneakerspot.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Clasa pentru utilizatorii care pot cumpăra sneakers și face oferte,
 * extinde User.
 */

public class Buyer extends User {
    private List<Order> orderHistory;
    private List<PriceOffer> activeOffers;

    public Buyer() {
        super();
        this.orderHistory = new ArrayList<>();
        this.activeOffers = new ArrayList<>();
    }

    public Buyer(int id, String username, String email, String hashedPassword) {
        super(id, username, email, hashedPassword);
        this.orderHistory = new ArrayList<>();
        this.activeOffers = new ArrayList<>();
    }

    // Plasare comandă actualizat
    public void placeOrder(Sneaker sneaker, Seller seller, int quantity) {
        if (sneaker.getStock() >= quantity) {
            double totalPrice = sneaker.getPrice() * quantity;
            Order order = new Order(
                    generateOrderId(),
                    sneaker,
                    this,
                    seller,
                    quantity,
                    totalPrice,
                    LocalDateTime.now(),
                    OrderStatus.PENDING
            );
            orderHistory.add(order);
            sneaker.decreaseStock(quantity);
        } else {
            throw new IllegalArgumentException("Nu există stoc suficient");
        }
    }

    // Anulare comandă
    public void cancelOrder(Order order) {
        if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.CANCELLED);
            order.getSneaker().increaseStock(order.getQuantity());
        } else {
            throw new IllegalStateException("Comanda nu mai poate fi anulată în statusul " + order.getStatus());
        }
    }

    // Oferă un preț
    public void makeOffer(Sneaker sneaker, BigDecimal offeredPrice) {
        PriceOffer offer = new PriceOffer(this, sneaker, offeredPrice);
        activeOffers.add(offer);
    }

    // Retrage o ofertă
    public void withdrawOffer(PriceOffer offer) {
        if (offer.getStatus() == OfferStatus.PENDING) {
            offer.setStatus(OfferStatus.WITHDRAWN);
            activeOffers.remove(offer);
        } else {
            throw new IllegalStateException("Oferta nu mai poate fi retrasă în statusul " + offer.getStatus());
        }
    }

    // Comenzi active
    public List<Order> getActiveOrders() {
        return orderHistory.stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING ||
                                 order.getStatus() == OrderStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    // Istoric oferte
    public List<PriceOffer> getAllOffers() {
        return new ArrayList<>(activeOffers);
    }

    // Verificare status comandă
    public OrderStatus checkOrderStatus(Order order) {
        if (!orderHistory.contains(order)) {
            throw new IllegalArgumentException("Comanda nu aparține acestui cumpărător");
        }
        return order.getStatus();
    }

    // Getters
    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    public List<PriceOffer> getActiveOffers() {
        return new ArrayList<>(activeOffers);
    }

    private int generateOrderId() {
        return orderHistory.size() + 1;
    }
}