package CarRental;

import CarRental.Admin.AdminDashboard;
import java.sql.*;
import javax.swing.*;

public class AdminLogin {
    public AdminLogin() {
        JFrame frame = new JFrame("Admin Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(120, 30, 170, 25);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(120, 70, 170, 25);
        frame.add(passText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 82, 25);
        frame.add(loginButton);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(210, 110, 78, 25);
        frame.add(backBtn);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            try (Connection conn = DatabaseConnection.connect()) {
                String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame, "Login Berhasil!");
                    new AdminDashboard();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Username atau password salah!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backBtn.addActionListener(e -> {
            new LoginMain();
            frame.dispose();
        });

        frame.setVisible(true);
    }
}

