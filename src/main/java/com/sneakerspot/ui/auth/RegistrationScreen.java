package com.sneakerspot.ui.auth;

import javax.swing.*;
import java.awt.*;

public class RegistrationScreen extends JFrame {
    public RegistrationScreen() {
        setTitle("SneakerSpot - Înregistrare");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Parolă:"));
        panel.add(passwordField);

        JButton registerButton = new JButton("Înregistrare");
        panel.add(registerButton);

        JButton backButton = new JButton("Înapoi");
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose();
        });

        // Poți adăuga aici acțiunea pentru butonul de înregistrare
    }
}