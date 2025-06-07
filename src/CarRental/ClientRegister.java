package CarRental;

import java.sql.*;
import javax.swing.*;

public class ClientRegister {
    public ClientRegister() {
        JFrame frame = new JFrame("Register Client");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // First Name
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(30, 30, 100, 25);
        frame.add(firstNameLabel);

        JTextField firstNameText = new JTextField();
        firstNameText.setBounds(140, 30, 200, 25);
        frame.add(firstNameText);

        // Last Name
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(30, 70, 100, 25);
        frame.add(lastNameLabel);

        JTextField lastNameText = new JTextField();
        lastNameText.setBounds(140, 70, 200, 25);
        frame.add(lastNameText);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 110, 100, 25);
        frame.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setBounds(140, 110, 200, 25);
        frame.add(emailText);

        // Phone Number
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(30, 150, 100, 25);
        frame.add(phoneLabel);

        JTextField phoneText = new JTextField();
        phoneText.setBounds(140, 150, 200, 25);
        frame.add(phoneText);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 190, 100, 25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(140, 190, 200, 25);
        frame.add(passText);

        // Register button
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(140, 240, 100, 30);
        frame.add(registerBtn);

        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(250, 240, 90, 30);
        frame.add(backBtn);

        // Action: Register
        registerBtn.addActionListener(e -> {
            String firstName = firstNameText.getText();
            String lastName = lastNameText.getText();
            String email = emailText.getText();
            String phone = phoneText.getText();
            String password = new String(passText.getPassword());

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua data harus diisi!");
                return;
            }

            try (Connection conn = DatabaseConnection.connect()) {
                String query = "INSERT INTO client (first_name, last_name, email, phone_number, password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.setString(4, phone);
                stmt.setString(5, password);

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

        // Action: Back
        backBtn.addActionListener(e -> {
            new RegisterMain();
            frame.dispose();
        });

        frame.setVisible(true);
    }
}
