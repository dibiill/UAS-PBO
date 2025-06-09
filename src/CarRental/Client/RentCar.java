package CarRental.Client;

import CarRental.DatabaseConnection;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class RentCar {
    private JFrame frame;
    private int clientId;

    /**
     * Konstruktor RentCar
    @param clientId ID client yang sedang login
     */
    public RentCar(int clientId) {
        this.clientId = clientId;
        frame = new JFrame("Rent a Car");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 255));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(58, 123, 213));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.WHITE));

        JLabel titleLabel = new JLabel("Available Cars for Rent", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JButton backBtnHeader = new JButton("⟵ Back");
        backBtnHeader.setFont(new Font("SansSerif", Font.BOLD, 13));
        backBtnHeader.setBackground(new Color(39, 174, 96));
        backBtnHeader.setForeground(Color.WHITE);
        backBtnHeader.setFocusPainted(false);
        backBtnHeader.setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14));
        backBtnHeader.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtnHeader.addActionListener(e -> {
            new ClientDashboard(clientId);
            frame.dispose();
        });
        headerPanel.add(backBtnHeader, BorderLayout.WEST);

        frame.add(headerPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Brand", "Model", "Year", "Price/Day", "Condition"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 15));
        table.setRowHeight(26);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.setBackground(new Color(100, 149, 237)); // Biru soft
        tableHeader.setForeground(Color.WHITE);

        // Center align kolom ID dan Year
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Year

        loadAvailableCars(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel bawah untuk input tanggal dan tombol
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 245, 255));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JLabel startLabel = new JLabel("Start Date (DD/MM/YYYY):");
        startLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField startDateField = new JTextField(10);
        startDateField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JLabel endLabel = new JLabel("End Date (DD/MM/YYYY):");
        endLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField endDateField = new JTextField(10);
        endDateField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JButton rentBtn = new JButton("Rent Selected Car");
        rentBtn.setFocusPainted(false);
        rentBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        rentBtn.setBackground(new Color(39, 174, 96));
        rentBtn.setForeground(Color.WHITE);
        rentBtn.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        rentBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(startLabel);
        bottomPanel.add(startDateField);
        bottomPanel.add(endLabel);
        bottomPanel.add(endDateField);
        bottomPanel.add(rentBtn);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action: Rent
        rentBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a car to rent.");
                return;
            }
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();
            if (startDate.isEmpty() || endDate.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both start and end date.");
                return;
            }
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate start = LocalDate.parse(startDate, formatter);
                LocalDate end = LocalDate.parse(endDate, formatter);
                if (end.isBefore(start)) {
                    JOptionPane.showMessageDialog(frame, "End date must be after start date.");
                    return;
                }
                int carId = (int) tableModel.getValueAt(selectedRow, 0);
                double pricePerDay = Double.parseDouble(tableModel.getValueAt(selectedRow, 4).toString().replace(",", ""));
                rentCar(carId, start.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), end.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE), pricePerDay);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Use DD/MM/YYYY.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /* Mengambil data mobil yang tersedia dari database dan mengisinya ke tabel.*/

    private void loadAvailableCars(DefaultTableModel tableModel) {
        String sql = "SELECT id_car, brand, model, year, price_per_day, `condition` FROM car WHERE `condition` = 'Good'";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_car");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                double price = rs.getDouble("price_per_day");
                String condition = rs.getString("condition");
                tableModel.addRow(new Object[]{
                    id, brand, model, year, String.format("%,.0f", price), condition
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Failed to load cars: " + e.getMessage());
        }
    }

    private void rentCar(int carId, String startDate, String endDate, double pricePerDay) {
        // Hitung total hari dan total harga
        long days = 1;
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            days = java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid date format.");
            return;
        }
        double totalPrice = pricePerDay * days;

        String sql = "INSERT INTO rent (client_id, car_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, carId);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            stmt.setDouble(5, totalPrice);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Car rented successfully!\nTotal price: Rp" + String.format("%,.0f", totalPrice));
                new ShowMyRentals(clientId);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to rent car.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage());
        }
    }
}
