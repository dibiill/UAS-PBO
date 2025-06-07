package CarRental.Admin;

import CarRental.DatabaseConnection;
import java.sql.*;
import javax.swing.*;

public class AddCar {
    public AddCar() {
        JFrame frame = new JFrame("Add New Car");
        frame.setSize(420, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setBounds(30, 30, 100, 25);
        frame.add(brandLabel);

        JTextField brandField = new JTextField();
        brandField.setBounds(150, 30, 215, 25);
        frame.add(brandField);

        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setBounds(30, 70, 100, 25);
        frame.add(modelLabel);

        JTextField modelField = new JTextField();
        modelField.setBounds(150, 70, 215, 25);
        frame.add(modelField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(30, 110, 100, 25);
        frame.add(yearLabel);

        JTextField yearField = new JTextField();
        yearField.setBounds(150, 110, 215, 25);
        frame.add(yearField);

        JLabel priceLabel = new JLabel("Price/Day:");
        priceLabel.setBounds(30, 150, 100, 25);
        frame.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(150, 150, 215, 25);
        frame.add(priceField);

        JLabel conditionLabel = new JLabel("Condition:");
        conditionLabel.setBounds(30, 190, 100, 25);
        frame.add(conditionLabel);

        JRadioButton conditionGood = new JRadioButton("Good");
        conditionGood.setBounds(150, 190, 80, 25);
        conditionGood.setSelected(true); 
        frame.add(conditionGood);

        JRadioButton conditionMaintenance = new JRadioButton("Under Maintenance");
        conditionMaintenance.setBounds(230, 190, 140, 25);
        frame.add(conditionMaintenance);

        ButtonGroup conditionGroup = new ButtonGroup();
        conditionGroup.add(conditionGood);
        conditionGroup.add(conditionMaintenance);

        JButton addBtn = new JButton("Add Car");
        addBtn.setBounds(150, 230, 110, 30);
        frame.add(addBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(270, 230, 95, 30);
        frame.add(backBtn);

        addBtn.addActionListener(e -> {
            String brand = brandField.getText();
            String model = modelField.getText();
            String yearText = yearField.getText();
            String priceText = priceField.getText();
            String condition = conditionGood.isSelected() ? "Good" : "Under Maintenance";

            if (brand.isEmpty() || model.isEmpty() || yearText.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua field wajib diisi!");
                return;
            }

            try {
                int year = Integer.parseInt(yearText);
                double price = Double.parseDouble(priceText);

                try (Connection conn = DatabaseConnection.connect()) {
                    String query = "INSERT INTO car (brand, model, year, price_per_day, `condition`) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, brand);
                    stmt.setString(2, model);
                    stmt.setInt(3, year);
                    stmt.setDouble(4, price);
                    stmt.setString(5, condition);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "Mobil berhasil ditambahkan!");
                        brandField.setText("");
                        modelField.setText("");
                        yearField.setText("");
                        priceField.setText("");
                        conditionGood.setSelected(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Gagal menambahkan mobil.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Kesalahan koneksi atau query.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Tahun dan harga harus berupa angka.");
            }
        });

        backBtn.addActionListener(e -> {
            new ViewCars();
            frame.dispose();
        });

        frame.setVisible(true);
    }
}

