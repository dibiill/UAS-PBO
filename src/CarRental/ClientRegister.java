package CarRental;

import java.sql.*;
import javax.swing.*;

public class ClientRegister {
    public ClientRegister() {
        JFrame frame = new JFrame("Register Client");
        frame.setSize(350, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 30, 80, 25);
        frame.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setBounds(120, 30, 180, 25);
        frame.add(emailText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(120, 70, 180, 25);
        frame.add(passText);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(120, 110, 100, 30);
        frame.add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(230, 110, 70, 30);
        frame.add(backBtn);

        registerBtn.addActionListener(e -> {
            String email = emailText.getText();
            String password = new String(passText.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Data tidak boleh kosong!");
                return;
            }

            try (Connection conn = DatabaseConnection.connect()) {
                String query = "INSERT INTO client (email, password) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, password);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(frame, "Registrasi berhasil!");
                    new LoginMain();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Registrasi gagal.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan koneksi.");
            }
        });

        backBtn.addActionListener(e -> {
            new RegisterMain();
            frame.dispose();
        });

        frame.setVisible(true);
    }
}

