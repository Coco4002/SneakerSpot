package com.sneakerspot.dao;

import com.sneakerspot.model.Buyer;
import com.sneakerspot.model.Seller;
import com.sneakerspot.model.User;
import com.sneakerspot.util.PasswordUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Adaugă utilizator nou
    public static void addUser(User user, String role) {
        String sql = "INSERT INTO user (username, email, hashedPassword, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getHashedPassword());
            stmt.setString(4, role.toLowerCase());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Caută user după id
    public static User getUserById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserRs(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Caută user după username
public static User getUserByUsername(String username) {
    // Connect și query
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String role = rs.getString("role");
            if ("buyer".equalsIgnoreCase(role)) {
                Buyer buyer = new Buyer(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("hashedPassword")
                );
                buyer.setRole(role);
                return buyer;
            } else if ("seller".equalsIgnoreCase(role)) {
                Seller seller = new Seller(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("hashedPassword")
                );
                seller.setRole(role);
                return seller;
            } else {
                // dacă ai încă roluri, tratează separat
                return null;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    // Listare toți userii
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(extractUserRs(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Ștergere cont
    public static void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifică adresa de email
    public static void updateUserEmail(int userId, String newEmail) {
        String sql = "UPDATE user SET email = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifică rolul userului (buyer, seller, admin, etc)
    public static void updateUserRole(int userId, String newRole) {
        String sql = "UPDATE user SET role = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newRole.toLowerCase());
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Schimbă parola (parola se trimite deja hash-uită, vezi PasswordUtils)
    public static void changePassword(int userId, String newHashedPassword) {
        String sql = "UPDATE user SET hashedPassword = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newHashedPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Caută useri după rol (ex: buyer, seller)
    public static List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role.toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(extractUserRs(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Caută useri după nume (căutare parțială)
    public static List<User> searchUsersByName(String keyword) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username LIKE ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(extractUserRs(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Validare autentificare (parolă hashuită)
    public static boolean validateCredentials(String username, String plainPassword) {
        User user = getUserByUsername(username);
        if (user != null) {
            return PasswordUtils.checkPassword(plainPassword, user.getHashedPassword());
        }
        return false;
    }

    // Metodă internă pentru a extrage User-ul generic
    private static User extractUserRs(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String hashedPassword = rs.getString("hashedPassword");
        // String role = rs.getString("role");
        User user = new User(id, username, email, hashedPassword) {};
        user.setRole(rs.getString("role"));
        return user;
    }

    // Caută dacă există un user cu emailul dat
    public static boolean getUserByEmail(String email) {
        String sql = "SELECT 1 FROM user WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // returnează true dacă a fost găsit un user cu acest email
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}