package com.sneakerspot.dao;

import com.sneakerspot.model.Sneaker;
import com.sneakerspot.model.Seller;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SneakerDAOTest {
    private static Sneaker testSneaker;
    private static Seller testSeller;

    @BeforeAll
    static void beforeAll() {
        DatabaseInitializer.initialize();

        testSeller = new Seller(99, "tester", "tester@s.neak", "pw");
        testSneaker = new Sneaker(0,testSeller, "TestBrand", "TestDesc", 123.4, 42, 3, "test.jpg");
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void testAddSneaker() {
        SneakerDAO.addSneaker(testSneaker);
        List<Sneaker> all = SneakerDAO.getAllSneakers();
        assertTrue(all.stream().anyMatch(s -> "TestBrand".equals(s.getBrand())));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void testGetSneakerById() {
        List<Sneaker> all = SneakerDAO.getAllSneakers();
        Sneaker found = all.stream().filter(s -> "TestBrand".equals(s.getBrand())).findFirst().orElse(null);
        assertNotNull(found);
        Sneaker byId = SneakerDAO.getSneakerById(found.getId());
        assertNotNull(byId);
        assertEquals("TestBrand", byId.getBrand());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void testUpdateSneaker() {
        List<Sneaker> all = SneakerDAO.getAllSneakers();
        Sneaker found = all.stream().filter(s -> "TestBrand".equals(s.getBrand())).findFirst().orElse(null);
        assertNotNull(found);
        found.setPrice(777.0);
        SneakerDAO.updateSneaker(found);
        Sneaker updated = SneakerDAO.getSneakerById(found.getId());
        assertEquals(777.0, updated.getPrice());
    }
}