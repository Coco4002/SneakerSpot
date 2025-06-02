package com.sneakerspot.ui.dashboard;

import com.sneakerspot.model.Buyer;
import com.sneakerspot.model.Sneaker;
import com.sneakerspot.model.Order;
import com.sneakerspot.dao.SneakerDAO;
import com.sneakerspot.ui.auth.StartScreen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuyerDashboard extends JFrame {
    private Buyer buyer;
    private JList<Sneaker> sneakersList;
    private DefaultListModel<Sneaker> sneakersModel;
    private JButton placeOrderButton;
    private JButton viewOrdersButton;
    private JButton logoutButton;

    public BuyerDashboard(Buyer buyer) {
        this.buyer = buyer;
        setTitle("Buyer Dashboard - " + buyer.getUsername());

        sneakersModel = new DefaultListModel<>();
        sneakersList = new JList<>(sneakersModel);

        // Încarcă sneakers din DB (presupunem că există un SneakerDAO!)
        List<Sneaker> sneakersDisponibili = SneakerDAO.getAllAvailableSneakers();
        sneakersDisponibili.forEach(sneakersModel::addElement);

        placeOrderButton = new JButton("Plasează comandă");
        viewOrdersButton = new JButton("Vezi comenzi");
        logoutButton = new JButton("Logout");

        placeOrderButton.addActionListener(e -> {
            Sneaker selected = sneakersList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Selectează un sneaker!");
                return;
            }
            String qtyStr = JOptionPane.showInputDialog(this, "Cantitate:");
            try {
                int qty = Integer.parseInt(qtyStr);
                buyer.placeOrder(selected, selected.getSeller(), qty);
                JOptionPane.showMessageDialog(this, "Comandă plasată!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantitate invalidă!");
            } catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(this, iae.getMessage());
            }
        });

        viewOrdersButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Comenzile tale:\n");
            for (Order order : buyer.getOrderHistory()) {
                sb.append("ID: ").append(order.getId())
                        .append(", Sneaker: ").append(order.getSneaker().getBrand())
                        .append(" ").append(order.getSneaker().getDescription())
                        .append(", Status: ").append(order.getStatus())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        logoutButton.addActionListener(e -> {
            this.dispose();
            StartScreen start = new StartScreen();
            start.setVisible(true);
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonsPanel.add(placeOrderButton);
        buttonsPanel.add(viewOrdersButton);
        buttonsPanel.add(logoutButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(sneakersList), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}