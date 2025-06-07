package CarRental.Admin;

import CarRental.LoginMain;
import javax.swing.*;

public class AdminDashboard {
    public AdminDashboard() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(400, 290);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Welcome, Admin!");
        label.setBounds(150, 20, 200, 30);
        frame.add(label);

        JButton viewCarsBtn = new JButton("Manage Cars");
        viewCarsBtn.setBounds(100, 60, 200, 30);
        frame.add(viewCarsBtn);

        JButton viewRentsBtn = new JButton("View All Rents");
        viewRentsBtn.setBounds(100, 100, 200, 30);
        frame.add(viewRentsBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(100, 160, 200, 30);
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

