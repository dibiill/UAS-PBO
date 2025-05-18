package Login;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Car Rental System");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel label = new JLabel("Welcome to Car Rental System", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridy = 0;
        panel.add(label, gbc);

        JButton clientLoginBtn = new JButton("Client Login");
        clientLoginBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(clientLoginBtn, gbc);

        JButton clientRegisterBtn = new JButton("Register as Client");
        clientRegisterBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 2;
        panel.add(clientRegisterBtn, gbc);

        JButton adminLoginBtn = new JButton("Admin Login");
        adminLoginBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 3;
        panel.add(adminLoginBtn, gbc);

        // Action Listeners
        clientLoginBtn.addActionListener(e -> {
            dispose();
            new ClientLogin();
        });

        clientRegisterBtn.addActionListener(e -> {
            dispose();
            new ClientRegister();
        });

        adminLoginBtn.addActionListener(e -> {
            dispose();
            new AdminLogin();
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
