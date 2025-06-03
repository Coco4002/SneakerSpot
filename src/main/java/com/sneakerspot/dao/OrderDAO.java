package com.sneakerspot.dao;

import com.sneakerspot.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static void addOrder(Order order) {
        String sql = "INSERT INTO orders (sneaker_id, buyer_id, seller_id, quantity, order_date, status, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getSneaker().getId());
            stmt.setInt(2, order.getBuyer().getId());
            stmt.setInt(3, order.getSeller().getId());
            stmt.setInt(4, order.getQuantity());
            stmt.setString(5, order.getOrderDate().toString());
            stmt.setString(6, order.getStatus().name());
            stmt.setDouble(7, order.getTotalPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static List<Order> getOrdersByBuyerId(int buyerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE buyer_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, buyerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(extractOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static List<Order> getOrdersBySellerId(int sellerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE seller_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(extractOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static void updateOrderStatus(int orderId, OrderStatus newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus.name());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> getPendingOrdersBySellerId(int sellerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, s.brand, s.description, u.username as buyer_username " +
                     "FROM orders o " +
                     "JOIN sneaker s ON o.sneaker_id = s.id " +
                     "JOIN user u ON o.buyer_id = u.id " +
                     "WHERE o.seller_id = ? AND o.status = 'PENDING'";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = extractOrder(rs);
                    order.getSneaker().setBrand(rs.getString("brand"));
                    order.getSneaker().setDescription(rs.getString("description"));
                    Buyer buyer = (Buyer) order.getBuyer();
                    buyer.setUsername(rs.getString("buyer_username"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private static Order extractOrder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int sneakerId = rs.getInt("sneaker_id");
        int buyerId = rs.getInt("buyer_id");
        int sellerId = rs.getInt("seller_id");
        int quantity = rs.getInt("quantity");
        double totalPrice = rs.getDouble("totalPrice");
        LocalDateTime orderDate = LocalDateTime.parse(rs.getString("order_date"));
        OrderStatus status = OrderStatus.valueOf(rs.getString("status"));

        Sneaker sneaker = new Sneaker();
        sneaker.setId(sneakerId);

        Buyer buyer = new Buyer();
        buyer.setId(buyerId);

        Seller seller = new Seller();
        seller.setId(sellerId);

        return new Order(id, sneaker, buyer, seller, quantity, totalPrice, orderDate, status);
    }
}