package CarRental.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewAvailableCars extends JFrame implements ActionListener {
    JTable table;
    JButton backButton;

    public ViewAvailableCars() {
        setTitle("Mobil Tersedia");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Daftar Mobil Tersedia", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(new Color(100, 149, 237));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"ID Mobil", "Merek", "Model", "Tahun", "Harga per Hari", "Kondisi"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data mobil dari database (hanya yang kondisi "Good")
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_car_rental", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM car WHERE `condition` = 'Good'")) {

            while (rs.next()) {
                String id = rs.getString("id_car");
                String brand = rs.getString("brand");
                String modelStr = rs.getString("model");
                String year = rs.getString("year");
                String price = rs.getString("price_per_day");
                String condition = rs.getString("condition");

                model.addRow(new Object[]{id, brand, modelStr, year, price, condition});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data mobil: " + e.getMessage());
        }

        // Tombol Kembali
        backButton = new JButton("Kembali");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(new Color(100, 149, 237));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        backButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            new ClientDashboard(); 
        }
    }
}
