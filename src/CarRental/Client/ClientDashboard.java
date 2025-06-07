package CarRental.Client;

import CarRental.Session;
import CarRental.LoginMain;
import javax.swing.*;

public class ClientDashboard {
    public ClientDashboard() {
        JFrame frame = new JFrame("Client Dashboard");
        frame.setSize(400, 290);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Welcome, " + Session.clientFirstName + "!");
        label.setBounds(150, 20, 200, 30);
        frame.add(label);

        JButton viewCarsBtn = new JButton("View Available Cars");
        viewCarsBtn.setBounds(100, 60, 200, 30);
        frame.add(viewCarsBtn);

        JButton viewRentsBtn = new JButton("Show My Rentals");
        viewRentsBtn.setBounds(100, 100, 200, 30);
        frame.add(viewRentsBtn);

        JButton editInfoBtn = new JButton("Edit My Info"); 
        editInfoBtn.setBounds(100, 140, 200, 30);
        frame.add(editInfoBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(100, 180, 200, 30);
        frame.add(logoutBtn);

        // Action Events
        viewCarsBtn.addActionListener(e -> {
            new ViewAvailableCars();
            frame.dispose();
        });

        viewRentsBtn.addActionListener(e -> {
            new ShowMyRentals();
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

        frame.setVisible(true);
    }
}

