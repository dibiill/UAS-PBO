package CarRental.Admin;

import CarRental.DatabaseConnection;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ViewCars {
    public ViewCars() {
        JFrame frame = new JFrame("All Cars");
        frame.setSize(700, 410);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("List of All Cars");
        titleLabel.setBounds(280, 10, 200, 30);
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
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
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
        backBtn.setBounds(480, 320, 100, 30);
        frame.add(backBtn);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(70, 320, 80, 30);
        addBtn.setBackground(new Color(12, 173, 0));
        addBtn.setForeground(Color.WHITE);
        frame.add(addBtn);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(160, 320, 100, 30);
        updateBtn.setBackground(new Color(255, 205, 0)); 
        updateBtn.setForeground(Color.BLACK);
        frame.add(updateBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(270, 320, 100, 30);
        deleteBtn.setBackground(new Color(222, 25, 0));
        deleteBtn.setForeground(Color.WHITE);
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

            int id = (int) table.getValueAt(selectedRow, 0);
            String brand = (String) table.getValueAt(selectedRow, 1);
            String model = (String) table.getValueAt(selectedRow, 2);
            int year = (int) table.getValueAt(selectedRow, 3);
            double price = (double) table.getValueAt(selectedRow, 4);
            String condition = (String) table.getValueAt(selectedRow, 5);

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

        frame.setVisible(true);
    }
}

