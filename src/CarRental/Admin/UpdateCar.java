package CarRental.Admin;

import CarRental.DatabaseConnection;

import javax.swing.*;
import java.sql.*;

public class UpdateCar {
    public UpdateCar(int carId, String brandVal, String modelVal, int yearVal, double priceVal, String conditionVal) {
        JFrame frame = new JFrame("Update Car");
        frame.setSize(400, 370);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setBounds(30, 30, 100, 25);
        frame.add(brandLabel);

        JTextField brandField = new JTextField(brandVal);
        brandField.setBounds(150, 30, 200, 25);
        frame.add(brandField);

        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setBounds(30, 70, 100, 25);
        frame.add(modelLabel);

        JTextField modelField = new JTextField(modelVal);
        modelField.setBounds(150, 70, 200, 25);
        frame.add(modelField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(30, 110, 100, 25);
        frame.add(yearLabel);

        JTextField yearField = new JTextField(String.valueOf(yearVal));
        yearField.setBounds(150, 110, 200, 25);
        frame.add(yearField);

        JLabel priceLabel = new JLabel("Price/Day:");
        priceLabel.setBounds(30, 150, 100, 25);
        frame.add(priceLabel);

        JTextField priceField = new JTextField(String.valueOf(priceVal));
        priceField.setBounds(150, 150, 200, 25);
        frame.add(priceField);

        JLabel conditionLabel = new JLabel("Condition:");
        conditionLabel.setBounds(30, 190, 100, 25);
        frame.add(conditionLabel);

        JRadioButton goodBtn = new JRadioButton("Good");
        goodBtn.setBounds(150, 190, 80, 25);
        JRadioButton maintenanceBtn = new JRadioButton("Under Maintenance");
        maintenanceBtn.setBounds(230, 190, 120, 25);

        ButtonGroup conditionGroup = new ButtonGroup();
        conditionGroup.add(goodBtn);
        conditionGroup.add(maintenanceBtn);
        frame.add(goodBtn);
        frame.add(maintenanceBtn);

        if (conditionVal.equalsIgnoreCase("Good")) {
            goodBtn.setSelected(true);
        } else {
            maintenanceBtn.setSelected(true);
        }

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(150, 240, 100, 30);
        frame.add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(260, 240, 90, 30);
        frame.add(cancelBtn);

        saveBtn.addActionListener(e -> {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                int year = Integer.parseInt(yearField.getText());
                double price = Double.parseDouble(priceField.getText());
                String condition = goodBtn.isSelected() ? "Good" : "Under Maintenance";

                try (Connection conn = DatabaseConnection.connect()) {
                    String query = "UPDATE car SET brand=?, model=?, year=?, price_per_day=?, `condition`=? WHERE id_car=?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, brand);
                    stmt.setString(2, model);
                    stmt.setInt(3, year);
                    stmt.setDouble(4, price);
                    stmt.setString(5, condition);
                    stmt.setInt(6, carId);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "Data mobil berhasil diupdate.");
                        new ViewCars();
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Gagal update data mobil.");
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Pastikan semua data valid.");
                ex.printStackTrace();
            }
        });

        cancelBtn.addActionListener(e -> {
            new ViewCars();
            frame.dispose();
        });

        frame.setVisible(true);
    }
}

