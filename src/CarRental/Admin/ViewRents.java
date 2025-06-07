package CarRental.Admin;

import CarRental.DatabaseConnection;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewRents {
    public ViewRents() {
        JFrame frame = new JFrame("All Rental Transactions");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("List of All Rents");
        titleLabel.setBounds(320, 10, 200, 30);
        frame.add(titleLabel);

        String[] columnNames = {"Rent ID", "Client Email", "Car", "Rent Date", "Return Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 50, 720, 250);
        frame.add(scrollPane);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(340, 320, 100, 30);
        frame.add(backBtn);

        backBtn.addActionListener(e -> {
            new AdminDashboard();
            frame.dispose();
        });

        try (Connection conn = DatabaseConnection.connect()) {
            String query = """
                SELECT rent.id, client.email, 
                       CONCAT(car.brand, ' ', car.model) AS car_name, 
                       rent.rent_date, rent.return_date
                FROM rent
                JOIN client ON rent.id_client = client.id
                JOIN car ON rent.id_car = car.id
            """;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rentId = rs.getInt("id");
                String email = rs.getString("email");
                String carName = rs.getString("car_name");
                Date rentDate = rs.getDate("rent_date");
                Date returnDate = rs.getDate("return_date");

                Object[] data = {rentId, email, carName, rentDate, returnDate};
                tableModel.addRow(data);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Gagal mengambil data dari database.");
        }

        frame.setVisible(true);
    }
}

