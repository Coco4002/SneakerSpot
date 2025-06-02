package com.sneakerspot.ui.dashboard;

import com.sneakerspot.model.Seller;
import com.sneakerspot.model.Sneaker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;


public class ManageSneakersFrame extends JFrame {
    private Seller seller;
    private DefaultTableModel sneakersTableModel;
    // Variabilă pentru calea imaginii selectate
    private String selectedImagePath = null; 

    public ManageSneakersFrame(Seller seller) {
        this.seller = seller;
        setTitle("Administrează Sneakers");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 1. Adaugă coloana "Poză"
        sneakersTableModel = new DefaultTableModel(new Object[]{"ID", "Brand", "Descriere", "Preț", "Mărime", "Stoc", "Poză"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) return ImageIcon.class;
                return super.getColumnClass(column);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toate celulele read-only
            }
        };

        JTable sneakersTable = new JTable(sneakersTableModel) {
            // Ridică puțin rândurile tabelului ca să se vadă thumbnail-ul
            @Override
            public int getRowHeight() {
                return 70;
            }
        };
        sneakersTable.setRowHeight(70); // thumbnail de 70px

        // Renderer ca să afișeze ImageIcon-ul ca pe poză
        sneakersTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public void setValue(Object value) {
                if (value instanceof ImageIcon) {
                    setIcon((ImageIcon) value);
                    setText("");
                } else {
                    setIcon(null);
                    setText("Fără poză");
                }
            }
        });

        mainPanel.add(new JScrollPane(sneakersTable), BorderLayout.CENTER);

        loadSneakers();

        // Formular de adăugare
        JPanel formPanel = new JPanel();
        JTextField brandField = new JTextField(8);
        JTextField descField = new JTextField(8);
        JTextField pretField = new JTextField(4);
        JTextField marimeField = new JTextField(3);
        JTextField stocField = new JTextField(3);

        // Elemente pentru poză
        JButton chooseImageButton = new JButton("Alege poză");
        JLabel imageLabel = new JLabel("Nicio imagine selectată");

        chooseImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath = selectedFile.getAbsolutePath();
                imageLabel.setText(selectedFile.getName());
            }
        });

        JButton addBtn = new JButton("Adaugă sneaker");

        formPanel.add(new JLabel("Brand:"));
        formPanel.add(brandField);
        formPanel.add(new JLabel("Descriere:"));
        formPanel.add(descField);
        formPanel.add(new JLabel("Preț:"));
        formPanel.add(pretField);
        formPanel.add(new JLabel("Mărime:"));
        formPanel.add(marimeField);
        formPanel.add(new JLabel("Stoc:"));
        formPanel.add(stocField);

        // Adaugă componentele pentru poză
        formPanel.add(chooseImageButton);
        formPanel.add(imageLabel);

        formPanel.add(addBtn);

        mainPanel.add(formPanel, BorderLayout.SOUTH);

        // ATENȚIE: la adăugare
        addBtn.addActionListener(e -> {
            try {
                String brand = brandField.getText();
                String desc = descField.getText();
                int pret = Integer.parseInt(pretField.getText());
                int marime = Integer.parseInt(marimeField.getText());
                int stoc = Integer.parseInt(stocField.getText());

                if (selectedImagePath == null || selectedImagePath.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Te rugăm să alegi o poză!", "Lipsă poză", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Sneaker newSneaker = new Sneaker(seller, brand, desc, pret, marime, stoc, selectedImagePath);
                seller.addSneaker(newSneaker);

                // Creează un thumbnail
                ImageIcon icon = new ImageIcon(selectedImagePath);
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                ImageIcon thumbnail = new ImageIcon(img);

                sneakersTableModel.addRow(new Object[]{
                    newSneaker.getId(), brand, desc, pret, marime, stoc, thumbnail
                });

                // Golește câmpurile
                brandField.setText("");
                descField.setText("");
                pretField.setText("");
                marimeField.setText("");
                stocField.setText("");
                selectedImagePath = null;
                imageLabel.setText("Nicio imagine selectată");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Date invalide!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        setContentPane(mainPanel);
    }

    private void loadSneakers() {
        sneakersTableModel.setRowCount(0);
        for (Sneaker s : seller.getSneakers()) {
            // Thumbnail pentru fiecare sneaker
            ImageIcon icon = null;
            if (s.getImagePath() != null && !s.getImagePath().isEmpty()) {
                icon = new ImageIcon(
                    new ImageIcon(s.getImagePath()).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
                );
            }
            sneakersTableModel.addRow(new Object[]{
                s.getId(), s.getBrand(), s.getDescription(), s.getPrice(), s.getSize(), s.getStock(), icon
            });
        }
    }
}