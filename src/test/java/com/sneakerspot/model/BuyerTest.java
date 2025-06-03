package com.sneakerspot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyerTest {
    private Buyer buyer;
    private Seller seller;
    private Sneaker sneaker;

    @BeforeEach
    void setUp() {
        seller = new Seller(1, "testSeller", "seller@test.com", "hashedPassword");
        buyer = new Buyer(2, "testBuyer", "buyer@test.com", "buyerPassword");
        sneaker = new Sneaker(seller, "Nike", "Air Max", 199.99, 42, 10, "image.jpg");
    }

    @Test
    void constructor() {
        Buyer paramBuyer = new Buyer(3, "buyer3", "buyer3@test.com", "pwd123");
        assertEquals(3, paramBuyer.getId());
        assertEquals("buyer3", paramBuyer.getUsername());
        assertEquals("buyer3@test.com", paramBuyer.getEmail());
        assertEquals("pwd123", paramBuyer.getHashedPassword());
        assertTrue(paramBuyer.getOrderHistory().isEmpty());
        assertTrue(paramBuyer.getActiveOffers().isEmpty());

        Buyer defaultBuyer = new Buyer();
        assertEquals(0, defaultBuyer.getId());
        assertNull(defaultBuyer.getUsername());
        assertNull(defaultBuyer.getEmail());
        assertNull(defaultBuyer.getHashedPassword());
        assertTrue(defaultBuyer.getOrderHistory().isEmpty());
        assertTrue(defaultBuyer.getActiveOffers().isEmpty());
    }

    @Test
    void placeOrder() {
        buyer.placeOrder(sneaker, seller, 2);

        List<Order> orderHistory = buyer.getOrderHistory();
        assertEquals(1, orderHistory.size());

        Order order = orderHistory.get(0);
        assertEquals(1, order.getId());
        assertEquals(sneaker, order.getSneaker());
        assertEquals(buyer, order.getBuyer());
        assertEquals(seller, order.getSeller());
        assertEquals(2, order.getQuantity());
        assertEquals(199.99 * 2, order.getTotalPrice());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertNotNull(order.getOrderDate());

        assertEquals(8, sneaker.getStock());
    }

    @Test
    void placeOrderException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            buyer.placeOrder(sneaker, seller, 15);
        });

        assertEquals("Nu există stoc suficient", exception.getMessage());
        assertEquals(10, sneaker.getStock());
    }

    @Test
    void cancelOrder() {
        buyer.placeOrder(sneaker, seller, 3);
        List<Order> orderHistory = buyer.getOrderHistory();
        Order order = orderHistory.get(0);

        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(7, sneaker.getStock());

        buyer.cancelOrder(order);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals(10, sneaker.getStock());
    }

    @Test
    void cancelOrderException() {
        buyer.placeOrder(sneaker, seller, 1);
        Order order = buyer.getOrderHistory().get(0);
        order.setStatus(OrderStatus.CONFIRMED);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            buyer.cancelOrder(order);
        });

        assertTrue(exception.getMessage().contains("Comanda nu mai poate fi anulată"));
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertEquals(9, sneaker.getStock());
    }

    @Test
    void makeOffer() {
        buyer.makeOffer(sneaker, new BigDecimal("150.00"));

        List<PriceOffer> activeOffers = buyer.getActiveOffers();
        assertEquals(1, activeOffers.size());

        PriceOffer offer = activeOffers.get(0);
        assertEquals(buyer, offer.getBuyer());
        assertEquals(sneaker, offer.getSneaker());
        assertEquals(new BigDecimal("150.00"), offer.getOfferedPrice());
        assertEquals(OfferStatus.PENDING, offer.getStatus());
    }

    @Test
    void withdrawOffer() {
        buyer.makeOffer(sneaker, new BigDecimal("180.00"));
        PriceOffer offer = buyer.getActiveOffers().get(0);

        buyer.withdrawOffer(offer);

        assertEquals(OfferStatus.WITHDRAWN, offer.getStatus());
        assertTrue(buyer.getActiveOffers().isEmpty());
    }

    @Test
    void withdrawOfferException() {
        buyer.makeOffer(sneaker, new BigDecimal("160.00"));
        PriceOffer offer = buyer.getActiveOffers().get(0);
        offer.setStatus(OfferStatus.ACCEPTED);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            buyer.withdrawOffer(offer);
        });

        assertTrue(exception.getMessage().contains("Oferta nu mai poate fi retrasă"));
        assertEquals(OfferStatus.ACCEPTED, offer.getStatus());
        assertEquals(1, buyer.getActiveOffers().size());
    }

    @Test
    void getActiveOrders() {
        buyer.placeOrder(sneaker, seller, 1);
        buyer.placeOrder(sneaker, seller, 1);
        buyer.placeOrder(sneaker, seller, 1);

        List<Order> orders = buyer.getOrderHistory();
        orders.get(0).setStatus(OrderStatus.PENDING);
        orders.get(1).setStatus(OrderStatus.CONFIRMED);
        orders.get(2).setStatus(OrderStatus.DELIVERED);

        List<Order> activeOrders = buyer.getActiveOrders();
        assertEquals(2, activeOrders.size());
        assertTrue(activeOrders.contains(orders.get(0)));
        assertTrue(activeOrders.contains(orders.get(1)));
        assertFalse(activeOrders.contains(orders.get(2)));
    }

    @Test
    void getAllOffers() {
        buyer.makeOffer(sneaker, new BigDecimal("150.00"));
        buyer.makeOffer(sneaker, new BigDecimal("160.00"));

        List<PriceOffer> allOffers = buyer.getAllOffers();
        assertEquals(2, allOffers.size());

        allOffers.clear();
        assertEquals(2, buyer.getActiveOffers().size());
    }

    @Test
    void checkOrderStatus() {
        buyer.placeOrder(sneaker, seller, 1);
        Order order = buyer.getOrderHistory().get(0);

        OrderStatus status = buyer.checkOrderStatus(order);
        assertEquals(OrderStatus.PENDING, status);

        order.setStatus(OrderStatus.CONFIRMED);
        status = buyer.checkOrderStatus(order);
        assertEquals(OrderStatus.CONFIRMED, status);
    }

    @Test
    void checkOrderStatusException() {
        Buyer otherBuyer = new Buyer(3, "otherBuyer", "other@test.com", "pwd");
        otherBuyer.placeOrder(sneaker, seller, 1);
        Order otherOrder = otherBuyer.getOrderHistory().get(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            buyer.checkOrderStatus(otherOrder);
        });

        assertEquals("Comanda nu aparține acestui cumpărător", exception.getMessage());
    }

    @Test
    void getOrderHistory() {
        buyer.placeOrder(sneaker, seller, 1);

        List<Order> orderHistory = buyer.getOrderHistory();
        assertEquals(1, orderHistory.size());

        orderHistory.clear();
        assertEquals(1, buyer.getOrderHistory().size());
    }

    @Test
    void getActiveOffers() {
        buyer.makeOffer(sneaker, new BigDecimal("170.00"));

        List<PriceOffer> activeOffers = buyer.getActiveOffers();
        assertEquals(1, activeOffers.size());

        activeOffers.clear();
        assertEquals(1, buyer.getActiveOffers().size());
    }
}