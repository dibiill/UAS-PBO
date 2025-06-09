package CarRental.Admin;

import CarRental.DatabaseConnection;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewCars {
    public ViewCars() {
        JFrame frame = new JFrame("All Cars");
        frame.setSize(700, 410);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        frame.getContentPane().setBackground(new Color(245, 245, 255));

        JLabel titleLabel = new JLabel("List of All Cars", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBounds(200, 10, 300, 30);
        frame.add(titleLabel);

        String[] columnNames = {"No.", "ID", "Brand", "Model", "Year", "Price/Day", "Condition"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
        return false;
        }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);

        // Tambahkan pengaturan warna header tabel di sini:
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.setBackground(new Color(58, 123, 213)); // Biru gelap
        tableHeader.setForeground(Color.WHITE);

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);  // No. (angka)
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);  // ID (angka)
        table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);   // Brand (teks)
        table.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);   // Model (teks)
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);  // Year (angka)
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);  // Price/Day (angka)
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Condition (kategori)

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 50, 620, 250);
        frame.add(scrollPane);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBounds(480, 320, 100, 32);
        frame.add(backBtn);

        JButton addBtn = new JButton("Add");
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        addBtn.setBackground(new Color(39, 174, 96));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setBounds(70, 320, 100, 32);
        frame.add(addBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        updateBtn.setBackground(new Color(52, 152, 219));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFocusPainted(false);
        updateBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBtn.setBounds(180, 320, 110, 32);
        frame.add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setBounds(300, 320, 110, 32);
        frame.add(deleteBtn);

        backBtn.addActionListener(e -> {
            new AdminDashboard();
            frame.dispose();
        });

        addBtn.addActionListener(e -> {
            new AddCar();
            frame.dispose();
        });

        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih satu baris untuk di-update.");
                return;
            }

            int id = (int) table.getValueAt(selectedRow, 1); // kolom 1 = id_car
            String brand = (String) table.getValueAt(selectedRow, 2);
            String model = (String) table.getValueAt(selectedRow, 3);
            int year = (int) table.getValueAt(selectedRow, 4);
            // Kolom 5 adalah priceFormatted (String), harus diubah ke double
            String priceStr = (String) table.getValueAt(selectedRow, 5);
            double price = Double.parseDouble(priceStr.replace(",", "")); // hilangkan koma
            String condition = (String) table.getValueAt(selectedRow, 6);

            new UpdateCar(id, brand, model, year, price, condition);
            frame.dispose();
        });

        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih satu baris untuk dihapus.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(frame, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) table.getValueAt(selectedRow, 0);
                try (Connection conn = DatabaseConnection.connect()) {
                    String deleteSQL = "DELETE FROM car WHERE id_car = ?";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
                    deleteStmt.setInt(1, id);
                    deleteStmt.executeUpdate();
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                    JOptionPane.showMessageDialog(frame, "Data berhasil dihapus.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Gagal menghapus data.");
                }
            }
        });

        frame.setVisible(true);

        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT * FROM car";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int no = 1; 
            while (rs.next()) {
                int id = rs.getInt("id_car"); 
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                double price = rs.getDouble("price_per_day");
                String priceFormatted = String.format("%,.0f", price);
                String condition = rs.getString("condition");

                Object[] data = {no++, id, brand, model, year, priceFormatted, condition};
                tableModel.addRow(data); 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
            JOptionPane.showMessageDialog(frame, "Gagal mengambil data dari database.");
        }
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);  
        table.getColumnModel().getColumn(6).setPreferredWidth(150); 

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

