package CarRental.Client;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewAvailableCars extends JFrame implements ActionListener {
    JTable table;
    JButton backButton;
    private int clientId;

    public ViewAvailableCars(int clientId) {
        this.clientId = clientId;

        setTitle("Mobil Tersedia");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255));

        JLabel header = new JLabel("Daftar Mobil Tersedia", JLabel.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setOpaque(true);
        // Gradasi biru ke biru muda dengan border bawah putih tipis
        header.setBackground(new Color(58, 123, 213));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE),
            BorderFactory.createEmptyBorder(18, 10, 18, 10)
        ));
        mainPanel.add(header, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"ID Mobil", "Merek", "Model", "Tahun", "Harga per Hari", "Kondisi"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));
        bottomPanel.setBackground(new Color(245, 245, 255));

        backButton = new JButton("⟵ Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        backButton.setBackground(new Color(52, 152, 219));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(9, 32, 9, 32));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);

        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            new ClientDashboard(clientId);
        }
    }
}
