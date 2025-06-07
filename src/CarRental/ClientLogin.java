package CarRental;

import java.sql.*;
import javax.swing.*;

import CarRental.Client.ClientDashboard;

public class ClientLogin {
    public ClientLogin() {
        JFrame frame = new JFrame("Client Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 30, 80, 25);
        frame.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setBounds(120, 30, 170, 25);
        frame.add(emailText);

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
            String email = emailText.getText();
            String password = new String(passText.getPassword());

            try (Connection conn = DatabaseConnection.connect()) {
                String sql = "SELECT * FROM client WHERE email = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int clientId = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    Session.setSession(clientId, firstName);
                    JOptionPane.showMessageDialog(frame, "Login Berhasil!");
                    new ClientDashboard();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Email atau Password salah!");
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

