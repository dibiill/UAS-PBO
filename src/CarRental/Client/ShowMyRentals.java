package CarRental.Client;

import CarRental.DatabaseConnection;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ShowMyRentals {
    private JFrame frame;
    private int clientId; // ID client yang sedang login

    /**
     * Konstruktor ShowMyRentals
     * @param clientId ID client yang sedang login, digunakan untuk filter data rental
     */
    public ShowMyRentals(int clientId) {
        this.clientId = clientId;
        frame = new JFrame("My Rental History");
        frame.setSize(850, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 255));

        // Judul
        JLabel titleLabel = new JLabel("History & Active Car Rentals", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(58, 123, 213));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 0, Color.WHITE),
            BorderFactory.createEmptyBorder(18, 10, 18, 10)
        ));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Tabel untuk menampilkan data rental
        String[] columnNames = {"No", "Car", "Start Date", "End Date", "Total Price", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(100, 149, 237));
        table.getTableHeader().setForeground(Color.WHITE);

        // Center align kolom No dan Status
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // No
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Status

        // Ambil data rental dari database
        loadRentalData(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Tombol kembali
        JButton backBtn = new JButton("Back");
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBorder(BorderFactory.createEmptyBorder(8, 28, 8, 28));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new ClientDashboard(clientId);
            frame.dispose();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(245, 245, 255));
        btnPanel.add(backBtn);
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadRentalData(DefaultTableModel tableModel) {
        // Query disesuaikan dengan struktur tabel 'rent' dan 'car'
        String sql = "SELECT r.id, c.brand, c.model, r.start_date, r.end_date, r.total_price " +
                     "FROM rent r " +
                     "JOIN car c ON r.car_id = c.id_car " +
                     "WHERE r.client_id = ? " +
                     "ORDER BY r.start_date DESC";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            int no = 1;
            while (rs.next()) {
                String car = rs.getString("brand") + " " + rs.getString("model");
                String start = rs.getString("start_date");
                String end = rs.getString("end_date");
                double totalPrice = rs.getDouble("total_price");
                String status;
                if (end == null || end.isEmpty()) {
                    status = "Active";
                } else {
                    LocalDate endDate = LocalDate.parse(end);
                    status = endDate.isBefore(LocalDate.now().plusDays(1)) ? "Finished" : "Active";
                }
                tableModel.addRow(new Object[]{
                    no++, car, start, (end == null ? "-" : end), String.format("Rp%,.0f", totalPrice), status
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Gagal mengambil data rental: " + e.getMessage());
        }
    }
}
