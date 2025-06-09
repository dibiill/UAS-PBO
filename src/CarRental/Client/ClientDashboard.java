package CarRental.Client;

import CarRental.LoginMain;
import CarRental.Session;
import java.awt.*;
import javax.swing.*;

public class ClientDashboard {
    public ClientDashboard(int clientId) {
        JFrame frame = new JFrame("Client Dashboard");
        frame.setSize(420, 370);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(245, 245, 255));
        frame.setContentPane(mainPanel);

        JLabel label = new JLabel("Welcome, " + Session.clientFirstName + "!", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setBounds(60, 24, 300, 32);
        mainPanel.add(label);

        int btnWidth = 220, btnHeight = 38, startX = 100, gap = 16;
        int y = 70;

        JButton viewCarsBtn = new JButton("View Available Cars");
        viewCarsBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewCarsBtn.setBackground(new Color(100, 149, 237));
        viewCarsBtn.setForeground(Color.WHITE);
        viewCarsBtn.setFocusPainted(false);
        viewCarsBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        viewCarsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewCarsBtn.setBounds(startX, y, btnWidth, btnHeight);
        mainPanel.add(viewCarsBtn);

        y += btnHeight + gap;

        JButton rentCarBtn = new JButton("Rent A Car");
        rentCarBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        rentCarBtn.setBackground(new Color(52, 152, 219));
        rentCarBtn.setForeground(Color.WHITE);
        rentCarBtn.setFocusPainted(false);
        rentCarBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        rentCarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rentCarBtn.setBounds(startX, y, btnWidth, btnHeight);
        mainPanel.add(rentCarBtn);

        y += btnHeight + gap;

        JButton viewRentsBtn = new JButton("Show My Rentals");
        viewRentsBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewRentsBtn.setBackground(new Color(100, 149, 237));
        viewRentsBtn.setForeground(Color.WHITE);
        viewRentsBtn.setFocusPainted(false);
        viewRentsBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        viewRentsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewRentsBtn.setBounds(startX, y, btnWidth, btnHeight);
        mainPanel.add(viewRentsBtn);

        y += btnHeight + gap;

        JButton editInfoBtn = new JButton("Edit My Info");
        editInfoBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        editInfoBtn.setBackground(new Color(39, 174, 96));
        editInfoBtn.setForeground(Color.WHITE);
        editInfoBtn.setFocusPainted(false);
        editInfoBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        editInfoBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editInfoBtn.setBounds(startX, y, btnWidth, btnHeight);
        mainPanel.add(editInfoBtn);

        y += btnHeight + gap;

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setBounds(startX, y, btnWidth, btnHeight);
        mainPanel.add(logoutBtn);

        // Action Events
        viewCarsBtn.addActionListener(e -> {
            new ViewAvailableCars(clientId);
            frame.dispose();
        });

        rentCarBtn.addActionListener(e -> {
            new RentCar(clientId);
            frame.dispose();
        });

        viewRentsBtn.addActionListener(e -> {
            new ShowMyRentals(clientId); 
            frame.dispose();
        });

        editInfoBtn.addActionListener(e -> {
            new EditClientInfo();
            frame.dispose();
        });

        logoutBtn.addActionListener(e -> {
            Session.clearSession();
            new LoginMain();
            frame.dispose();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

