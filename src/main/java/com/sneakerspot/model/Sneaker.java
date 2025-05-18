package com.sneakerspot.model;

public class Sneaker {
    private int id;
    private String brand;
    private String description;
    private double price;
    private int size;
    private String condition; // "Nou" sau "Second-hand"
    private int stock;
    private String imagePath;
    private int sellerId;

    public Sneaker(int id, String brand, String description, double price, int size, String condition, int stock, String imagePath, int sellerId) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.size = size;
        this.condition = condition;
        this.stock = stock;
        this.imagePath = imagePath;
        this.sellerId = sellerId;
    }

    // Getters
    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getSize() { return size; }
    public String getCondition() { return condition; }
    public int getStock() { return stock; }
    public String getImagePath() { return imagePath; }
    public int getSellerId() { return sellerId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setSize(int size) { this.size = size; }
    public void setCondition(String condition) { this.condition = condition; }
    public void setStock(int stock) { this.stock = stock; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }
}
