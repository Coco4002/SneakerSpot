package com.sneakerspot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:sneakerspot.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC"); // Explicitly load the SQLite JDBC driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found. Please ensure it is in the classpath.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:sneakerspot.db");
    }
}