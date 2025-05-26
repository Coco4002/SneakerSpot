package com.sneakerspot.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clasa pentru utilizatorii care pot cumpăra produse și face oferte
 */

public class Buyer {
    private String username;
    private String email;
    private String password;
    private List<Order> orderHistory;
    private List<PriceOffer> activeOffers;

    //Constructor
    public Buyer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.orderHistory = new ArrayList<>();
        this.activeOffers = new ArrayList<>();
    }

    // Metodă pentru a face o comandă
    public void placeOrder(Product product, int quantity) {
        if (product.getStock() >= quantity) {
            Order order = new Order(this, product, quantity);
            orderHistory.add(order);
            product.decreaseStock(quantity);
        } else {
            throw new IllegalArgumentException("Nu există stoc suficient");
        }
    }

    // Metodă pentru anularea comenzii
    public void cancelOrder(Order order) {
        if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.CANCELLED);
            order.getProduct().increaseStock(order.getQuantity());
        } else {
            throw new IllegalStateException("Comanda nu mai poate fi anulată în statusul " + order.getStatus());
        }
    }

    // Metodă pentru a face o ofertă de preț
    public void makeOffer(Product product, BigDecimal offeredPrice) {
        PriceOffer offer = new PriceOffer(this, product, offeredPrice);
        activeOffers.add(offer);
    }

    // Metodă pentru retragerea unei oferte
    public void withdrawOffer(PriceOffer offer) {
        if (offer.getStatus() == OfferStatus.PENDING) {
            offer.setStatus(OfferStatus.WITHDRAWN);
            activeOffers.remove(offer);
        } else {
            throw new IllegalStateException("Oferta nu mai poate fi retrasă în statusul " + offer.getStatus());
        }
    }

    // Metodă pentru vizualizarea comenzilor active
    public List<Order> getActiveOrders() {
        return orderHistory.stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING || 
                               order.getStatus() == OrderStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    // Metodă pentru vizualizarea istoricului complet de oferte
    public List<PriceOffer> getAllOffers() {
        return new ArrayList<>(activeOffers);
    }

    // Metodă pentru verificarea statusului unei comenzi specifice
    public OrderStatus checkOrderStatus(Order order) {
        if (!orderHistory.contains(order)) {
            throw new IllegalArgumentException("Comanda nu aparține acestui cumpărător");
        }
        return order.getStatus();
    }

    // Getters
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