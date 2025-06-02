package com.sneakerspot.dao;

import com.sneakerspot.model.Sneaker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SneakerDAO {

    // Adaugă un sneaker nou
    public static void addSneaker(Sneaker sneaker) {
        String sql = "INSERT INTO sneaker (brand, description, price, size, stock, imagePath, seller_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sneaker.getBrand());
            stmt.setString(2, sneaker.getDescription());
            stmt.setDouble(3, sneaker.getPrice());
            stmt.setInt(4, sneaker.getSize());
            stmt.setInt(5, sneaker.getStock());
            stmt.setString(6, sneaker.getImagePath());
            stmt.setInt(7, sneaker.getSeller().getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returnează toți sneakerșii
    public static List<Sneaker> getAllSneakers() {
        List<Sneaker> sneakers = new ArrayList<>();
        String sql = "SELECT * FROM sneaker";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sneakers.add(extractSneaker(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sneakers;
    }

    // Găsește sneaker după ID
    public static Sneaker getSneakerById(int id) {
        String sql = "SELECT * FROM sneaker WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractSneaker(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizează datele unui sneaker
    public static void updateSneaker(Sneaker sneaker) {
        String sql = "UPDATE sneaker SET brand = ?, description = ?, price = ?, size = ?, stock = ?, imagePath = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sneaker.getBrand());
            stmt.setString(2, sneaker.getDescription());
            stmt.setDouble(3, sneaker.getPrice());
            stmt.setInt(4, sneaker.getSize());
            stmt.setInt(5, sneaker.getStock());
            stmt.setString(6, sneaker.getImagePath());
            stmt.setInt(7, sneaker.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Șterge sneaker după ID
    public static void deleteSneaker(int id) {
        String sql = "DELETE FROM sneaker WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodă utilitară pentru mapare ResultSet -> Sneaker
    private static Sneaker extractSneaker(ResultSet rs) throws SQLException {
        Sneaker sneaker = new Sneaker();
        sneaker.setId(rs.getInt("id"));
        sneaker.setBrand(rs.getString("brand"));
        sneaker.setDescription(rs.getString("description"));
        sneaker.setPrice(rs.getDouble("price"));
        sneaker.setSize(rs.getInt("size"));
        sneaker.setStock(rs.getInt("stock"));
        sneaker.setImagePath(rs.getString("imagePath"));
        return sneaker;
    }

    public static List<Sneaker> getAllAvailableSneakers() {
        List<Sneaker> all = getAllSneakers();
        List<Sneaker> available = new ArrayList<>();
        for (Sneaker sneaker : all) {
            if (sneaker.getStock() > 0) {
                available.add(sneaker);
            }
        }
        return available;
    }
public static List<Sneaker> getSneakersBySellerId(int sellerId) {
    List<Sneaker> sneakers = new ArrayList<>();
    String sql = "SELECT * FROM sneaker WHERE seller_id = ?";
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, sellerId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sneakers.add(extractSneaker(rs));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return sneakers;
}
}