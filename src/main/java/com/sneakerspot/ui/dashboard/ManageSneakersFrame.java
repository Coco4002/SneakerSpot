package com.sneakerspot.ui.dashboard;

import com.sneakerspot.model.Seller;
import com.sneakerspot.model.Sneaker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;

import com.sneakerspot.dao.SneakerDAO;


public class ManageSneakersFrame extends JFrame {
    private Seller seller;
    private DefaultTableModel sneakersTableModel;
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

        sneakersTableModel = new DefaultTableModel(new Object[]{"ID", "Brand", "Descriere", "Preț", "Mărime", "Stoc", "Poză"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) return ImageIcon.class;
                return super.getColumnClass(column);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable sneakersTable = new JTable(sneakersTableModel) {
            @Override
            public int getRowHeight() {
                return 70;
            }
        };
        sneakersTable.setRowHeight(70);

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

        JPanel formPanel = new JPanel();
        JTextField brandField = new JTextField(8);
        JTextField descField = new JTextField(8);
        JTextField pretField = new JTextField(4);
        JTextField marimeField = new JTextField(3);
        JTextField stocField = new JTextField(3);

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

        formPanel.add(chooseImageButton);
        formPanel.add(imageLabel);

        formPanel.add(addBtn);

        JButton deleteBtn = new JButton("Șterge sneaker");
        formPanel.add(deleteBtn);
        JButton updateBtn = new JButton("Salvează modificările");
        formPanel.add(updateBtn);

        mainPanel.add(formPanel, BorderLayout.SOUTH);

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

                SneakerDAO.addSneaker(newSneaker);

                seller.addSneaker(newSneaker);

                ImageIcon icon = new ImageIcon(selectedImagePath);
                Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                ImageIcon thumbnail = new ImageIcon(img);

                sneakersTableModel.addRow(new Object[]{
                    newSneaker.getId(), brand, desc, pret, marime, stoc, thumbnail
                });

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

        deleteBtn.addActionListener(e -> {
            int selectedRow = sneakersTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selectează un sneaker pe care vrei să-l ștergi.", "Nimic selectat", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Ești sigur că vrei să ștergi sneakerul selectat?", "Confirmă ștergerea", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int sneakerId = (int) sneakersTableModel.getValueAt(selectedRow, 0);
                SneakerDAO.deleteSneaker(sneakerId);
                sneakersTableModel.removeRow(selectedRow);
            }
        });

        sneakersTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = sneakersTable.getSelectedRow();
            if (selectedRow != -1) {
                brandField.setText(sneakersTableModel.getValueAt(selectedRow, 1).toString());
                descField.setText(sneakersTableModel.getValueAt(selectedRow, 2).toString());
                pretField.setText(sneakersTableModel.getValueAt(selectedRow, 3).toString());
                marimeField.setText(sneakersTableModel.getValueAt(selectedRow, 4).toString());
                stocField.setText(sneakersTableModel.getValueAt(selectedRow, 5).toString());
            }
        });

        updateBtn.addActionListener(e -> {
            int selectedRow = sneakersTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selectează un sneaker din tabel pentru a-l edita.", "Nicio selecție", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = (int) sneakersTableModel.getValueAt(selectedRow, 0);
                String brand = brandField.getText();
                String desc = descField.getText();
                int pret = Integer.parseInt(pretField.getText());
                int marime = Integer.parseInt(marimeField.getText());
                int stoc = Integer.parseInt(stocField.getText());
                Sneaker sneakerToUpdate = SneakerDAO.getSneakerById(id);
                sneakerToUpdate.setBrand(brand);
                sneakerToUpdate.setDescription(desc);
                sneakerToUpdate.setPrice(pret);
                sneakerToUpdate.setSize(marime);
                sneakerToUpdate.setStock(stoc);
                SneakerDAO.updateSneaker(sneakerToUpdate);
                sneakersTableModel.setValueAt(brand, selectedRow, 1);
                sneakersTableModel.setValueAt(desc, selectedRow, 2);
                sneakersTableModel.setValueAt(pret, selectedRow, 3);
                sneakersTableModel.setValueAt(marime, selectedRow, 4);
                sneakersTableModel.setValueAt(stoc, selectedRow, 5);
                JOptionPane.showMessageDialog(this, "Modificările au fost salvate!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Date invalide sau eroare la salvare!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        setContentPane(mainPanel);
    }

    private void loadSneakers() {
        sneakersTableModel.setRowCount(0);
        java.util.List<Sneaker> userSneakers = SneakerDAO.getSneakersBySellerId(seller.getId());
        for (Sneaker s : userSneakers) {
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