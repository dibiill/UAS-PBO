package CarRental.Admin;

import CarRental.LoginMain;
import java.awt.*;
import javax.swing.*;

public class AdminDashboard {
    public AdminDashboard() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(400, 290);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(245, 245, 255));

        JLabel label = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setBounds(80, 20, 240, 32);
        frame.add(label);

        int btnWidth = 200, btnHeight = 36, startX = 100, gap = 18;
        int y = 70;

        JButton viewCarsBtn = new JButton("Manage Cars");
        viewCarsBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewCarsBtn.setBackground(new Color(100, 149, 237));
        viewCarsBtn.setForeground(Color.WHITE);
        viewCarsBtn.setFocusPainted(false);
        viewCarsBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        viewCarsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCarsBtn.setBounds(startX, y, btnWidth, btnHeight);
        frame.add(viewCarsBtn);

        y += btnHeight + gap;

        JButton viewRentsBtn = new JButton("View All Rents");
        viewRentsBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewRentsBtn.setBackground(new Color(52, 152, 219));
        viewRentsBtn.setForeground(Color.WHITE);
        viewRentsBtn.setFocusPainted(false);
        viewRentsBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        viewRentsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewRentsBtn.setBounds(startX, y, btnWidth, btnHeight);
        frame.add(viewRentsBtn);

        y += btnHeight + gap;

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setBounds(startX, y, btnWidth, btnHeight);
        frame.add(logoutBtn);

        // Action Events
        viewCarsBtn.addActionListener(e -> {
            new ViewCars();
            frame.dispose();
        });

        viewRentsBtn.addActionListener(e -> {
            new ViewRents();
            frame.dispose();
        });

        logoutBtn.addActionListener(e -> {
            new LoginMain();
            frame.dispose();
        });

        frame.setVisible(true);
    }
}

