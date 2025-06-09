package CarRental.Admin;

import CarRental.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ViewRents {
    public ViewRents() {
        JFrame frame = new JFrame("All Rental Transactions");
        frame.setSize(900, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(245, 245, 255));

        JLabel titleLabel = new JLabel("List of All Rents", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBounds(0, 10, 900, 32);
        frame.add(titleLabel);

        String[] columnNames = {"Rent ID", "Client Email", "Car", "Rent Date", "Return Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(100, 149, 237));
        table.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Rent ID
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Status

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 50, 820, 300);
        frame.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 255));
        buttonPanel.setBounds(0, 370, 900, 60);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JButton detailBtn = new JButton("Detail Car");
        detailBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailBtn.setBackground(new Color(39, 174, 96));
        detailBtn.setForeground(Color.WHITE);
        detailBtn.setFocusPainted(false);
        detailBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        detailBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton finishBtn = new JButton("Mark as Finished");
        finishBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        finishBtn.setBackground(new Color(52, 152, 219));
        finishBtn.setForeground(Color.WHITE);
        finishBtn.setFocusPainted(false);
        finishBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        finishBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(detailBtn);
        buttonPanel.add(finishBtn);
        buttonPanel.add(backBtn);
        frame.add(buttonPanel);

        // Load data
        try (Connection conn = DatabaseConnection.connect()) {
            String query = """
                SELECT rent.id, client.email, 
                       car.id_car, car.brand, car.model, car.year, car.price_per_day, car.condition,
                       rent.start_date, rent.end_date
                FROM rent
                JOIN client ON rent.client_id = client.id
                JOIN car ON rent.car_id = car.id_car
            """;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rentId = rs.getInt("id");
                String email = rs.getString("email");
                String carName = rs.getString("brand") + " " + rs.getString("model");
                String rentDate = rs.getString("start_date");
                String returnDate = rs.getString("end_date");
                String status;
                if (returnDate == null || returnDate.isEmpty()) {
                    status = "Active";
                } else {
                    LocalDate end = LocalDate.parse(returnDate);
                    status = end.isBefore(LocalDate.now().plusDays(1)) ? "Finished" : "Active";
                }
                Object[] data = {rentId, email, carName, rentDate, (returnDate == null ? "-" : returnDate), status};
                tableModel.addRow(data);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Gagal mengambil data dari database.");
        }

        // Detail Car Action
        detailBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih salah satu rental terlebih dahulu.");
                return;
            }
            int rentId = (int) tableModel.getValueAt(selectedRow, 0);
            showCarDetail(rentId);
        });

        // Mark as Finished Action
        finishBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih salah satu rental terlebih dahulu.");
                return;
            }
            String status = (String) tableModel.getValueAt(selectedRow, 5);
            if ("Finished".equals(status)) {
                JOptionPane.showMessageDialog(frame, "Rental sudah selesai.");
                return;
            }
            int rentId = (int) tableModel.getValueAt(selectedRow, 0);
            String today = LocalDate.now().toString();
            try (Connection conn = DatabaseConnection.connect()) {
                String update = "UPDATE rent SET end_date = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setString(1, today);
                stmt.setInt(2, rentId);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    tableModel.setValueAt(today, selectedRow, 4);
                    tableModel.setValueAt("Finished", selectedRow, 5);
                    JOptionPane.showMessageDialog(frame, "Rental berhasil ditandai selesai.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Gagal update status rental.");
            }
        });

        backBtn.addActionListener(e -> {
            new AdminDashboard();
            frame.dispose();
        });

        frame.setVisible(true);
    }

    // Menampilkan detail mobil berdasarkan rentId
    private void showCarDetail(int rentId) {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = """
                SELECT car.brand, car.model, car.year, car.price_per_day, car.condition
                FROM rent
                JOIN car ON rent.car_id = car.id_car
                WHERE rent.id = ?
            """;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, rentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String detail = "Brand: " + rs.getString("brand") +
                        "\nModel: " + rs.getString("model") +
                        "\nYear: " + rs.getInt("year") +
                        "\nPrice/Day: Rp" + String.format("%,.0f", rs.getDouble("price_per_day")) +
                        "\nCondition: " + rs.getString("condition");
                JOptionPane.showMessageDialog(null, detail, "Car Detail", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil detail mobil.");
        }
    }
}

