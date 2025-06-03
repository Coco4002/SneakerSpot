package com.sneakerspot.ui.dashboard;

import com.sneakerspot.model.Seller;

import javax.swing.*;
import java.awt.*;

public class SellerDashboard extends JFrame {

    private Seller seller;

    public SellerDashboard(Seller seller) {
        this.seller = seller;
        setTitle("Seller Dashboard - " + seller.getUsername());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bine ai venit, " + seller.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton manageSneakersButton = new JButton("Administrează sneakers");
        JButton viewOrdersButton = new JButton("Vezi comenzi");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(manageSneakersButton);
        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "Ai fost delogat!");
        });

        manageSneakersButton.addActionListener(e -> {
            new ManageSneakersFrame(seller).setVisible(true);
        });

        viewOrdersButton.addActionListener(e -> {
            new OrdersFrame(seller.getId()).setVisible(true);
        });

        panel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(panel);
    }
}