package com.sneakerspot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SneakerTest {

    private Sneaker sneaker;
    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller(1, "testSeller", "seller@test.com", "hashedPassword");
        sneaker = new Sneaker(1, seller, "Adidas", "Test", 299.99, 42, 10, "image.jpg");
    }

    @Test
    void constructor() {
        assertEquals(1, sneaker.getId());
        assertEquals(seller, sneaker.getSeller());
        assertEquals("Adidas", sneaker.getBrand());
        assertEquals("Test", sneaker.getDescription());
        assertEquals(299.99, sneaker.getPrice());
        assertEquals(42, sneaker.getSize());
        assertEquals(10, sneaker.getStock());
        assertEquals("image.jpg", sneaker.getImagePath());
    }

    @Test
    void defaultConstructor() {
        Sneaker emptySneaker = new Sneaker();
        assertEquals(0, emptySneaker.getId());
        assertNull(emptySneaker.getSeller());
        assertNull(emptySneaker.getBrand());
        assertNull(emptySneaker.getDescription());
        assertEquals(0.0, emptySneaker.getPrice());
        assertEquals(0, emptySneaker.getSize());
        assertEquals(0, emptySneaker.getStock());
        assertNull(emptySneaker.getImagePath());
    }

    @Test
    void setters() {
        Seller newSeller = new Seller(2, "otherSeller", "other@test.com", "hashedPwd");
        
        sneaker.setId(100);
        sneaker.setSeller(newSeller);
        sneaker.setBrand("Nike");
        sneaker.setDescription("Updated description");
        sneaker.setPrice(199.99);
        sneaker.setSize(44);
        sneaker.setStock(20);
        sneaker.setImagePath("new_image.jpg");
        
        assertEquals(100, sneaker.getId());
        assertEquals(newSeller, sneaker.getSeller());
        assertEquals("Nike", sneaker.getBrand());
        assertEquals("Updated description", sneaker.getDescription());
        assertEquals(199.99, sneaker.getPrice());
        assertEquals(44, sneaker.getSize());
        assertEquals(20, sneaker.getStock());
        assertEquals("new_image.jpg", sneaker.getImagePath());
    }

    @Test
    void getters() {
        assertEquals(1, sneaker.getId());
        assertEquals(seller, sneaker.getSeller());
        assertEquals("Adidas", sneaker.getBrand());
        assertEquals("Test", sneaker.getDescription());
        assertEquals(299.99, sneaker.getPrice());
        assertEquals(42, sneaker.getSize());
        assertEquals(10, sneaker.getStock());
        assertEquals("image.jpg", sneaker.getImagePath());
    }

    @Test
    void decreaseStock() {
        boolean result = sneaker.decreaseStock(5);
        assertTrue(result);
        assertEquals(5, sneaker.getStock());
    }

    @Test
    void decreaseStockFalse() {
        boolean result = sneaker.decreaseStock(15);
        assertFalse(result);
        assertEquals(10, sneaker.getStock());
    }

    @Test
    void decreaseStockFalseNegativeOrZero() {
        assertFalse(sneaker.decreaseStock(0));
        assertFalse(sneaker.decreaseStock(-3));
        assertEquals(10, sneaker.getStock());
    }

    @Test
    void decreaseStockZero() {
        boolean result = sneaker.decreaseStock(10);
        assertTrue(result);
        assertEquals(0, sneaker.getStock());
    }

    @Test
    void increaseStockPositiveQuantity() {
        sneaker.increaseStock(5);
        assertEquals(15, sneaker.getStock());
    }

    @Test
    void increaseStockNegativeOrZeroQuantity() {
        sneaker.increaseStock(0);
        assertEquals(10, sneaker.getStock());

        sneaker.increaseStock(-5);
        assertEquals(10, sneaker.getStock());
    }

    @Test
    void setStockNegative() {
        sneaker.setStock(-5);
        assertEquals(-5, sneaker.getStock());
    }

    @Test
    void setPriceNegative() {
        sneaker.setPrice(-10.99);
        assertEquals(-10.99, sneaker.getPrice());
    }

    @Test
    void incrementAndDecrementStock() {
        sneaker.increaseStock(5);
        assertEquals(15, sneaker.getStock());
        
        assertTrue(sneaker.decreaseStock(7));
        assertEquals(8, sneaker.getStock());
        
        sneaker.increaseStock(2);
        assertEquals(10, sneaker.getStock());
    }
}