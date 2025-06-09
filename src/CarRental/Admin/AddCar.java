package CarRental.Admin;

import CarRental.DatabaseConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AddCar {
    public AddCar() {
        JFrame frame = new JFrame("Add New Car");
        frame.setSize(420, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(245, 245, 255));

        JLabel titleLabel = new JLabel("Add New Car", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBounds(100, 10, 220, 30);
        frame.add(titleLabel);

        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        brandLabel.setBounds(30, 50, 100, 25);
        frame.add(brandLabel);

        JTextField brandField = new JTextField();
        brandField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        brandField.setBounds(150, 50, 215, 25);
        frame.add(brandField);

        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        modelLabel.setBounds(30, 90, 100, 25);
        frame.add(modelLabel);

        JTextField modelField = new JTextField();
        modelField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        modelField.setBounds(150, 90, 215, 25);
        frame.add(modelField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        yearLabel.setBounds(30, 130, 100, 25);
        frame.add(yearLabel);

        JTextField yearField = new JTextField();
        yearField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        yearField.setBounds(150, 130, 215, 25);
        frame.add(yearField);

        JLabel priceLabel = new JLabel("Price/Day:");
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        priceLabel.setBounds(30, 170, 100, 25);
        frame.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        priceField.setBounds(150, 170, 215, 25);
        frame.add(priceField);

        JLabel conditionLabel = new JLabel("Condition:");
        conditionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        conditionLabel.setBounds(30, 210, 100, 25);
        frame.add(conditionLabel);

        JRadioButton conditionGood = new JRadioButton("Good");
        conditionGood.setFont(new Font("SansSerif", Font.PLAIN, 14));
        conditionGood.setBackground(new Color(245, 245, 255));
        conditionGood.setBounds(150, 210, 80, 25);
        conditionGood.setSelected(true); 
        frame.add(conditionGood);

        JRadioButton conditionMaintenance = new JRadioButton("Under Maintenance");
        conditionMaintenance.setFont(new Font("SansSerif", Font.PLAIN, 14));
        conditionMaintenance.setBackground(new Color(245, 245, 255));
        conditionMaintenance.setBounds(230, 210, 140, 25);
        frame.add(conditionMaintenance);

        ButtonGroup conditionGroup = new ButtonGroup();
        conditionGroup.add(conditionGood);
        conditionGroup.add(conditionMaintenance);

        // Panel tombol rata tengah
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 255));
        buttonPanel.setBounds(0, 250, 420, 50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton addBtn = new JButton("Add Car");
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        addBtn.setBackground(new Color(39, 174, 96));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(addBtn);
        buttonPanel.add(backBtn);
        frame.add(buttonPanel);

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

