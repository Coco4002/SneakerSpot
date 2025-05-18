package com.sneakerspot.model;

public class Buyer {
    private String username;
    private String email;
    private String password;

    public Buyer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
