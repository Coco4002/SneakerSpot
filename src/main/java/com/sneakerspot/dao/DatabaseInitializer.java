package com.sneakerspot.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            // Tabela user (cu hashedPassword și role)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS user (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    email TEXT NOT NULL UNIQUE,
                    hashedPassword TEXT NOT NULL,
                    role TEXT NOT NULL
                );
            """);

            // Tabela seller (relatată cu user)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS seller (
                    id INTEGER PRIMARY KEY,
                    FOREIGN KEY (id) REFERENCES user(id)
                );
            """);

            // Tabela buyer (relatată cu user)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS buyer (
                    id INTEGER PRIMARY KEY,
                    FOREIGN KEY (id) REFERENCES user(id)
                );
            """);

            // Tabela sneaker
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS sneaker (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    brand TEXT NOT NULL,
                    description TEXT,
                    price REAL NOT NULL,
                    size INTEGER NOT NULL,
                    stock INTEGER NOT NULL,
                    imagePath TEXT
                )
            """);

            // Tabela order
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS "order" (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    buyer_id INTEGER NOT NULL,
                    seller_id INTEGER NOT NULL,
                    sneaker_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    order_date TEXT NOT NULL,
                    status TEXT NOT NULL,
                    FOREIGN KEY (buyer_id) REFERENCES buyer(id),
                    FOREIGN KEY (seller_id) REFERENCES seller(id),
                    FOREIGN KEY (sneaker_id) REFERENCES sneaker(id)
                );
            """);

            // Tabela price_offer
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS price_offer (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    buyer_id INTEGER NOT NULL,
                    sneaker_id INTEGER NOT NULL,
                    offered_price REAL NOT NULL,
                    offer_date TEXT NOT NULL,
                    FOREIGN KEY (buyer_id) REFERENCES buyer(id),
                    FOREIGN KEY (sneaker_id) REFERENCES sneaker(id)
                );
            """);

            System.out.println("Tabelele au fost inițializate!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}