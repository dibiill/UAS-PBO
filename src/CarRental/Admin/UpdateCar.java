package CarRental.Admin;

import CarRental.DatabaseConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UpdateCar {
    public UpdateCar(int carId, String brandVal, String modelVal, int yearVal, double priceVal, String conditionVal) {
        JFrame frame = new JFrame("Update Car");
        frame.setSize(400, 370);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(245, 245, 255));

        JLabel titleLabel = new JLabel("Update Car Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBounds(60, 10, 280, 30);
        frame.add(titleLabel);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        brandLabel.setBounds(30, 50, 100, 25);
        frame.add(brandLabel);

        JTextField brandField = new JTextField(brandVal);
        brandField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        brandField.setBounds(150, 50, 200, 25);
        frame.add(brandField);

        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        modelLabel.setBounds(30, 90, 100, 25);
        frame.add(modelLabel);

        JTextField modelField = new JTextField(modelVal);
        modelField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        modelField.setBounds(150, 90, 200, 25);
        frame.add(modelField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        yearLabel.setBounds(30, 130, 100, 25);
        frame.add(yearLabel);

        JTextField yearField = new JTextField(String.valueOf(yearVal));
        yearField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        yearField.setBounds(150, 130, 200, 25);
        frame.add(yearField);

        JLabel priceLabel = new JLabel("Price/Day:");
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        priceLabel.setBounds(30, 170, 100, 25);
        frame.add(priceLabel);

        JTextField priceField = new JTextField(String.valueOf(priceVal));
        priceField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        priceField.setBounds(150, 170, 200, 25);
        frame.add(priceField);

        JLabel conditionLabel = new JLabel("Condition:");
        conditionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        conditionLabel.setBounds(30, 210, 100, 25);
        frame.add(conditionLabel);

        JRadioButton goodBtn = new JRadioButton("Good");
        goodBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        goodBtn.setBackground(new Color(245, 245, 255));
        goodBtn.setBounds(150, 210, 80, 25);

        JRadioButton maintenanceBtn = new JRadioButton("Under Maintenance");
        maintenanceBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        maintenanceBtn.setBackground(new Color(245, 245, 255));
        maintenanceBtn.setBounds(230, 210, 140, 25);

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

        // Panel tombol rata tengah
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 255));
        buttonPanel.setBounds(0, 260, 400, 50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        saveBtn.setBackground(new Color(39, 174, 96));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(100, 149, 237));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        frame.add(buttonPanel);

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

