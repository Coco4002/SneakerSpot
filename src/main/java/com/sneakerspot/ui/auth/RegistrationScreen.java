package com.sneakerspot.ui.auth;

import com.sneakerspot.dao.UserDAO;
import com.sneakerspot.model.Buyer;
import com.sneakerspot.model.Seller;
import com.sneakerspot.model.User;
import com.sneakerspot.util.PasswordUtils;

import javax.swing.*;
import java.awt.*;

public class RegistrationScreen extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JButton backButton;

    public RegistrationScreen() {
        usernameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        roleComboBox = new JComboBox<>(new String[]{"buyer", "seller"});
        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleComboBox.getSelectedItem();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Toate câmpurile sunt obligatorii!");
                return;
            }

            if (UserDAO.getUserByUsername(username) != null) {
                JOptionPane.showMessageDialog(this, "Username deja existent!");
                return;
            }
            if (UserDAO.getUserByEmail(email) != false) {
                JOptionPane.showMessageDialog(this, "Email deja utilizat!");
                return;
            }

            String hashedPassword = PasswordUtils.hashPassword(password);
            User user;
            if ("seller".equals(role)) {
                user = new Seller(0, username, email, hashedPassword);
            } else {
                user = new Buyer(0, username, email, hashedPassword);
            }
            user.setRole(role); // <-- adaugă această linie!
            UserDAO.addUser(user, role);

            JOptionPane.showMessageDialog(this, "Cont creat cu succes! Te poți autentifica acum.");
            this.dispose();
            new LoginScreen().setVisible(true);
        });

        backButton.addActionListener(e -> {
            this.dispose();
            new LoginScreen().setVisible(true);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Parolă:"));
        panel.add(passwordField);
        panel.add(new JLabel("Rol:"));
        panel.add(roleComboBox);

        panel.add(registerButton);
        panel.add(backButton);

        this.setContentPane(panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}