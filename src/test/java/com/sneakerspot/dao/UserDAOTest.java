package com.sneakerspot.dao;

import com.sneakerspot.model.Buyer;
import com.sneakerspot.model.Seller;
import com.sneakerspot.model.User;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void testAddAndGetUser() {
        User user = new Buyer();
        user.setUsername("test_user");
        user.setEmail("test@example.com");
        user.setHashedPassword("testpasswordhash");
        String role = "buyer";

        // Adaugă user
        UserDAO.addUser(user, role);

        // Găsește user by username
        User found = UserDAO.getUserByUsername("test_user");
        assertNotNull(found);
        assertEquals("test_user", found.getUsername());
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void testUpdateAndDeleteUser() {
        // Adaugăm un user pentru test
        User user = new Seller();
        user.setUsername("delete_test");
        user.setEmail("delete@test.com");
        user.setHashedPassword("passhash");
        String role = "buyer";
        UserDAO.addUser(user, role);

        // Găsim userul
        User inserted = UserDAO.getUserByUsername("delete_test");

        // Actualizăm email-ul
        UserDAO.updateUserEmail(inserted.getId(), "newmail@test.com");
        User updated = UserDAO.getUserById(inserted.getId());
        assertEquals("newmail@test.com", updated.getEmail());

        // Ștergem userul
        UserDAO.deleteUser(inserted.getId());
        User deleted = UserDAO.getUserById(inserted.getId());
        assertNull(deleted);
    }
}