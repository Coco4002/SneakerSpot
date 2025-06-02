package com.sneakerspot.ui.auth;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("SneakerSpot - Autentificare");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Parolă:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        JButton registerButton = new JButton("Înregistrare");
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        // Exemplu: Acțiune pe butonul de înregistrare (deschide RegistrationScreen)
        registerButton.addActionListener(e -> {
            new RegistrationScreen().setVisible(true);
            dispose();
        });

        // Poți adăuga aici acțiunea pentru butonul de login
    }
}