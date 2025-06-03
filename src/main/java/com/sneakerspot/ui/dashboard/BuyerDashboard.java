package com.sneakerspot.ui.dashboard;

import com.sneakerspot.model.Buyer;
import com.sneakerspot.model.Sneaker;
import com.sneakerspot.model.Order;
import com.sneakerspot.dao.SneakerDAO;
import com.sneakerspot.ui.auth.StartScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BuyerDashboard extends JFrame {
    private Buyer buyer;
    private JList<Sneaker> sneakersList;
    private DefaultListModel<Sneaker> sneakersModel;
    private JButton placeOrderButton;
    private JButton viewOrdersButton;
    private JButton refreshButton;
    private JButton logoutButton;
    private JPanel detailsPanel;
    private JLabel imageLabel;
    private JLabel brandLabel;
    private JLabel descLabel;
    private JLabel priceLabel;
    private JLabel sellerLabel;
    private JLabel stockLabel;

    private JTextField brandFilterField;
    private JTextField pretMinFilterField;
    private JTextField pretMaxFilterField;
    private JTextField marimeFilterField;
    private JButton filterButton;

    public BuyerDashboard(Buyer buyer) {
        this.buyer = buyer;
        setTitle("SneakerSpot - Buyer Dashboard");

        sneakersModel = new DefaultListModel<>();
        sneakersList = new JList<>(sneakersModel);

        sneakersList.setCellRenderer(new SneakerCellRenderer());
        sneakersList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        sneakersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadSneakers();

        sneakersList.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2 && sneakersList.getSelectedValue() != null) {
            showSneakerDetails(sneakersList.getSelectedValue());
        }
    }
});

        placeOrderButton = new JButton("\uD83D\uDED2 Plasează comandă");
        placeOrderButton.setBackground(new Color(52, 152, 219));
        placeOrderButton.setForeground(Color.WHITE);

        viewOrdersButton = new JButton("\uD83D\uDCCB Vezi comenzi");
        viewOrdersButton.setBackground(new Color(46, 204, 113));
        viewOrdersButton.setForeground(Color.WHITE);

        refreshButton = new JButton("\u21BA Refresh");
        refreshButton.setBackground(new Color(241, 196, 15));
        refreshButton.setForeground(Color.WHITE);

        logoutButton = new JButton("\uD83D\uDEAA Logout");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);

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

        refreshButton.addActionListener(e -> loadSneakers());

        logoutButton.addActionListener(e -> {
            this.dispose();
            StartScreen start = new StartScreen();
            start.setVisible(true);
        });

        JLabel welcomeLabel = new JLabel("Bun venit, " + buyer.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new GridLayout(2, 5, 3, 2));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtrare"));
        brandFilterField = new JTextField();
        pretMinFilterField = new JTextField();
        pretMaxFilterField = new JTextField();
        marimeFilterField = new JTextField();
        filterButton = new JButton("Filtrează");

        filterPanel.add(new JLabel("Brand:"));
        filterPanel.add(brandFilterField);
        filterPanel.add(new JLabel("Preț min:"));
        filterPanel.add(pretMinFilterField);
        filterPanel.add(new JLabel("Preț max:"));
        filterPanel.add(pretMaxFilterField);
        filterPanel.add(new JLabel("Mărime:"));
        filterPanel.add(marimeFilterField);
        filterPanel.add(new JLabel(""));
        filterPanel.add(filterButton);

        filterButton.addActionListener(e -> loadSneakersWithFilter());

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4, 12, 0));
        buttonsPanel.setBackground(new Color(245, 245, 245));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        buttonsPanel.add(placeOrderButton);
        buttonsPanel.add(viewOrdersButton);
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(logoutButton);

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalii Sneaker"));

        imageLabel = new JLabel();
        brandLabel = new JLabel();
        descLabel = new JLabel();
        priceLabel = new JLabel();
        sellerLabel = new JLabel();
        stockLabel = new JLabel();

        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        detailsPanel.add(imageLabel);
        detailsPanel.add(brandLabel);
        detailsPanel.add(descLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(sellerLabel);
        detailsPanel.add(stockLabel);

        setLayout(new BorderLayout(10, 6));
        add(topPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.BEFORE_FIRST_LINE);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(sneakersList), detailsPanel);
        splitPane.setDividerLocation(230);
        add(splitPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        sneakersList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Sneaker selected = sneakersList.getSelectedValue();
                updateDetailsPanel(selected);
            }
        });

        setSize(650, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void loadSneakers() {
        sneakersModel.clear();
        List<Sneaker> sneakersDisponibili = SneakerDAO.getAllAvailableSneakers();
        sneakersDisponibili.forEach(sneakersModel::addElement);
    }

    private void showSneakerDetails(Sneaker sneaker) {
        JOptionPane.showMessageDialog(this,
                "Brand: " + sneaker.getBrand() +
                        "\nDescriere: " + sneaker.getDescription() +
                        "\nPreț: " + sneaker.getPrice() + " RON" +
                        "\nVânzător: " + sneaker.getSeller().getUsername() +
                        "\nStoc: " + sneaker.getStock(),
                "Detalii Sneaker", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateDetailsPanel(Sneaker sneaker) {
        if (sneaker == null) {
            imageLabel.setIcon(null);
            brandLabel.setText("");
            descLabel.setText("");
            priceLabel.setText("");
            sellerLabel.setText("");
            stockLabel.setText("");
            return;
        }

        if (sneaker.getImagePath() != null && !sneaker.getImagePath().isEmpty()) {
            ImageIcon icon = new ImageIcon(sneaker.getImagePath());
            Image img = icon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setIcon(null);
        }

        brandLabel.setText("Brand: " + sneaker.getBrand());
        descLabel.setText("Descriere: " + sneaker.getDescription());
        priceLabel.setText("Preț: " + sneaker.getPrice() + " RON");
        sellerLabel.setText("Vânzător: " + sneaker.getSeller().getUsername());
        stockLabel.setText("Stoc: " + sneaker.getStock());
    }

    private void loadSneakersWithFilter() {
        sneakersModel.clear();
        String brand = brandFilterField.getText().trim().toLowerCase();
        String pretMinText = pretMinFilterField.getText().trim();
        String pretMaxText = pretMaxFilterField.getText().trim();
        String marimeText = marimeFilterField.getText().trim();

        List<Sneaker> sneakersDisponibili = SneakerDAO.getAllAvailableSneakers();
        for (Sneaker s : sneakersDisponibili) {
            boolean match = true;
            if (!brand.isEmpty() && !s.getBrand().toLowerCase().contains(brand)) match = false;
            if (!pretMinText.isEmpty()) {
                try {
                    double min = Double.parseDouble(pretMinText);
                    if (s.getPrice() < min) match = false;
                } catch (NumberFormatException ignored) {}
            }
            if (!pretMaxText.isEmpty()) {
                try {
                    double max = Double.parseDouble(pretMaxText);
                    if (s.getPrice() > max) match = false;
                } catch (NumberFormatException ignored) {}
            }
            if (!marimeText.isEmpty()) {
                if (!String.valueOf(s.getSize()).equals(marimeText)) match = false;
            }
            if (match) sneakersModel.addElement(s);
        }
    }
    
    private static class SneakerCellRenderer extends JPanel implements ListCellRenderer<Sneaker> {
        private final JLabel brand = new JLabel();
        private final JLabel desc = new JLabel();
        private final JLabel price = new JLabel();

        public SneakerCellRenderer() {
            setLayout(new BorderLayout(8,0));
            setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
            brand.setFont(new Font("Segoe UI", Font.BOLD, 16));
            desc.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            price.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            add(brand, BorderLayout.WEST);
            JPanel mp = new JPanel(new GridLayout(2, 1));
            mp.setOpaque(false);
            mp.add(desc);
            mp.add(price);
            add(mp, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Sneaker> list, Sneaker value, int index, boolean isSelected, boolean cellHasFocus) {
            brand.setText(value.getBrand());
            desc.setText(value.getDescription());
            price.setText("Preț: " + value.getPrice() + " RON");
            setBackground(isSelected ? new Color(186, 225, 255) : Color.WHITE);
            setOpaque(true);
            return this;
        }
    }
}