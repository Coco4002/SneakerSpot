package com.sneakerspot.ui.dashboard;

import com.sneakerspot.dao.OrderDAO;
import com.sneakerspot.model.Order;
import com.sneakerspot.model.OrderStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrdersFrame extends JFrame {
    private int sellerId;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private List<Order> orders;

    public OrdersFrame(int sellerId) {
        this.sellerId = sellerId;
        initializeUI();
        loadOrders();
    }

    private void initializeUI() {
        setTitle("Comenzile Primite");
        setSize(950, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {
                "ID", "Produs", "Brand", "Cumpărător", "Cantitate",
                "Preț Total", "Data Comandă", "Status", "Acțiuni"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        ordersTable = new JTable(tableModel);
        ordersTable.setRowHeight(35);

        ordersTable.getColumnModel().getColumn(8).setCellRenderer(new ActionButtonRenderer());
        ordersTable.getColumnModel().getColumn(8).setCellEditor(new ActionButtonEditor());

        JScrollPane scrollPane = new JScrollPane(ordersTable);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Actualizează");
        refreshBtn.addActionListener(e -> loadOrders());
        JButton closeBtn = new JButton("Închide");
        closeBtn.addActionListener(e -> dispose());
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        orders = OrderDAO.getOrdersBySellerId(sellerId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Order order : orders) {
            Object[] rowData = {
                    order.getId(),
                    order.getSneaker().getDescription(),
                    order.getSneaker().getBrand(),
                    order.getBuyer().getUsername(),
                    order.getQuantity(),
                    String.format("%.2f Lei", order.getTotalPrice()),
                    order.getOrderDate().format(formatter),
                    order.getStatus().name(),
                    ""
            };
            tableModel.addRow(rowData);
        }
    }

    private class ActionButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton acceptBtn = new JButton("Acceptă");
        private JButton rejectBtn = new JButton("Respinge");

        public ActionButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 7, 0));
            acceptBtn.setBackground(new Color(76, 175, 80));
            acceptBtn.setForeground(Color.WHITE);
            acceptBtn.setFocusPainted(false);

            rejectBtn.setBackground(new Color(244, 67, 54));
            rejectBtn.setForeground(Color.WHITE);
            rejectBtn.setFocusPainted(false);

            add(acceptBtn);
            add(rejectBtn);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();
            String status = (String) table.getValueAt(row, 7);

            if ("PENDING".equals(status)) {
                add(acceptBtn);
                add(rejectBtn);
            } else {
                JLabel statusLabel = new JLabel(status);
                statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
                add(statusLabel);
            }

            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    private class ActionButtonEditor extends DefaultCellEditor {
        private JPanel panel = new JPanel();
        private JButton acceptBtn = new JButton("Acceptă");
        private JButton rejectBtn = new JButton("Respinge");
        private int currentRow = -1;

        public ActionButtonEditor() {
            super(new JCheckBox());
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 0));
            acceptBtn.setBackground(new Color(76, 175, 80));
            acceptBtn.setForeground(Color.WHITE);
            acceptBtn.setFocusPainted(false);

            rejectBtn.setBackground(new Color(244, 67, 54));
            rejectBtn.setForeground(Color.WHITE);
            rejectBtn.setFocusPainted(false);

            panel.add(acceptBtn);
            panel.add(rejectBtn);

            acceptBtn.addActionListener(e -> handleAction(OrderStatus.CONFIRMED));
            rejectBtn.addActionListener(e -> handleAction(OrderStatus.CANCELLED));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            String status = (String) table.getValueAt(row, 7);
            acceptBtn.setVisible("PENDING".equals(status));
            rejectBtn.setVisible("PENDING".equals(status));
            return panel;
        }

        private void handleAction(OrderStatus newStatus) {
            if (currentRow >= 0 && currentRow < orders.size()) {
                Order order = orders.get(currentRow);
                int confirm = JOptionPane.showConfirmDialog(
                        OrdersFrame.this,
                        "Sigur doriți să " + (newStatus == OrderStatus.CONFIRMED ? "acceptați" : "respingeți")
                                + " comanda #" + order.getId() + "?",
                        "Confirmare", JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        OrderDAO.updateOrderStatus(order.getId(), newStatus);
                        order.setStatus(newStatus);
                        tableModel.setValueAt(newStatus.name(), currentRow, 7);
                        JOptionPane.showMessageDialog(OrdersFrame.this,
                                (newStatus == OrderStatus.CONFIRMED ? "Comanda acceptată!" : "Comanda respinsă!"));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(OrdersFrame.this,
                                "Eroare la actualizare: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            fireEditingStopped();
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}