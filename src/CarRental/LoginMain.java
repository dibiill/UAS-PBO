package CarRental;

import javax.swing.*;

public class LoginMain {
    public LoginMain() {
        JFrame frame = new JFrame("Login Menu");
        frame.setSize(350, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Login as:");
        label.setBounds(150, 20, 200, 30);
        frame.add(label);

        JButton adminBtn = new JButton("Admin");
        adminBtn.setBounds(100, 70, 150, 30);
        frame.add(adminBtn);

        JButton clientBtn = new JButton("Client");
        clientBtn.setBounds(100, 110, 150, 30);
        frame.add(clientBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(100, 170, 150, 30);
        frame.add(backBtn);

        adminBtn.addActionListener(e -> {
            new AdminLogin();
            frame.dispose();
        });

        clientBtn.addActionListener(e -> {
            new ClientLogin();
            frame.dispose();
        });

        backBtn.addActionListener(e -> {
            new RegisterMain();
            frame.dispose();
        });

        frame.setVisible(true);
    }
}

