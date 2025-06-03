package com.sneakerspot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestSeller {
    private Seller seller;
    private Sneaker sneaker1;
    private Sneaker sneaker2;

    @BeforeEach
    void setUp() {
        seller = new Seller(10, "username", "email@test.com", "pw");
        sneaker1 = new Sneaker(seller, "Adidas", "desc", 100, 40, 2, "img1.jpg");
        sneaker2 = new Sneaker(seller, "Nike", "desc2", 200, 41, 3, "img2.jpg");
    }

    @Test
    void constructorFaraArgumente() {
        Seller s = new Seller();
        assertNotNull(s.getSneakers());
        assertNotNull(s.getOrderHistory());
        assertEquals(0, s.getSneakers().size());
        assertEquals(0, s.getOrderHistory().size());
    }

    @Test
    void constructorCuArgumente() {
        Seller s = new Seller(1, "user", "e@x.com", "p");
        assertNotNull(s.getSneakers());
        assertNotNull(s.getOrderHistory());
        assertEquals(0, s.getSneakers().size());
        assertEquals(0, s.getOrderHistory().size());
    }

    @Test
    void addSneakers() {
        seller.addSneaker(sneaker1);
        seller.addSneaker(sneaker2);
        List<Sneaker> sneakers = seller.getSneakers();
        assertEquals(2, sneakers.size());
        assertTrue(sneakers.contains(sneaker1));
        assertTrue(sneakers.contains(sneaker2));
    }

    @Test
    void removeSneakerDacaExista() {
        seller.addSneaker(sneaker1);
        seller.removeSneaker(sneaker1);
        assertEquals(0, seller.getSneakers().size());
    }

    @Test
    void removeSneakerDacaNuExista() {
        seller.addSneaker(sneaker1);
        seller.removeSneaker(sneaker2);
        assertEquals(1, seller.getSneakers().size());
    }

    @Test
    void getSneakersWorksAfterMutations() {
        assertEquals(0, seller.getSneakers().size());
        seller.addSneaker(sneaker1);
        assertEquals(1, seller.getSneakers().size());
        seller.removeSneaker(sneaker1);
        assertEquals(0, seller.getSneakers().size());
    }

    @Test
    void addOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        seller.addOrder(order1);
        seller.addOrder(order2);
        List<Order> history = seller.getOrderHistory();
        assertEquals(2, history.size());
        assertTrue(history.contains(order1));
        assertTrue(history.contains(order2));
    }

    @Test
    void getOrderHistory() {
        Order order1 = new Order();
        seller.addOrder(order1);
        seller.addSneaker(sneaker1);
        seller.getSneakers().clear();
        assertEquals(1, seller.getOrderHistory().size());
    }
}