package com.sneakerspot.model;

public class Sneaker {
    private int id;
    private Seller seller;
    private String brand;
    private String description;
    private double price;
    private int size;
    private int stock;
    private String imagePath;

    public Sneaker() {}

    public Sneaker(int id, Seller seller, String brand, String description, double price,
                   int size, int stock, String imagePath) {
        this.id = id;
        this.seller = seller;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.size = size;
        this.stock = stock;
        this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public Seller getSeller() { return seller; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getSize() { return size; }
    public int getStock() { return stock; }
    public String getImagePath() { return imagePath; }

    public void setId(int id) { this.id = id; }
    public void setSeller(Seller seller) { this.seller = seller; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setSize(int size) { this.size = size; }
    public void setStock(int stock) { this.stock = stock; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public boolean decreaseStock(int quantity) {
        if (quantity > 0 && stock >= quantity) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    public void increaseStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }
}