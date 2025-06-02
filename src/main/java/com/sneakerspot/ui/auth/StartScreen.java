package com.sneakerspot.ui.auth;

import javax.swing.*;

public class StartScreen extends JFrame {
    public StartScreen() {
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Înregistrare");

        loginBtn.addActionListener(e -> {
            this.dispose(); // Închide start screen
            new LoginScreen().setVisible(true);
        });

        registerBtn.addActionListener(e -> {
            this.dispose();
            new RegistrationScreen().setVisible(true);
        });

        JPanel panel = new JPanel();
        panel.add(loginBtn);
        panel.add(registerBtn);

        this.setTitle("Bun venit!");
        this.setContentPane(panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}