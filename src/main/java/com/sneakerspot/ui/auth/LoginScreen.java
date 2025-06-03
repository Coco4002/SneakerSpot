package com.sneakerspot.ui.auth;

import com.sneakerspot.dao.UserDAO;
import com.sneakerspot.model.Buyer;
import com.sneakerspot.model.Seller;
import com.sneakerspot.model.User;
import com.sneakerspot.util.PasswordUtils;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = UserDAO.getUserByUsername(username);
        if (user != null && PasswordUtils.checkPassword(password, user.getHashedPassword())) {
        } else {
            // Eroare
        }

            if (user != null && PasswordUtils.checkPassword(password, user.getHashedPassword())) {
                JOptionPane.showMessageDialog(this, "Autentificare reușită!");

                String rol = user.getRole();
                if (rol != null) rol = rol.toUpperCase();

                switch (rol) {
                    case "BUYER":
                        new com.sneakerspot.ui.dashboard.BuyerDashboard((Buyer) user).setVisible(true);
                        break;
                    case "SELLER":
                        new com.sneakerspot.ui.dashboard.SellerDashboard((Seller) user).setVisible(true);
                        break;
                    case "ADMIN":
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Rol necunoscut!");
                        System.out.println("Rol necunoscut: " + rol);
                        this.dispose();
                        return;
                }

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Utilizator sau parolă incorectă!");
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Parolă:"));
        panel.add(passwordField);
        panel.add(loginButton);

        this.setContentPane(panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}