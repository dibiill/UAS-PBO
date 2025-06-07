package CarRental.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import CarRental.DatabaseConnection;
import CarRental.Session;

public class EditClientInfo extends JFrame implements ActionListener {
    private JTextField firstNameField, lastNameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JButton saveButton, backButton;

    public EditClientInfo() {
        setTitle("Edit Profil");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Edit Informasi Client", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(60, 10, 280, 30);
        add(title);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(30, 60, 100, 25);
        add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(140, 60, 200, 25);
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(30, 100, 100, 25);
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(140, 100, 200, 25);
        add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 140, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(140, 140, 200, 25);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(30, 180, 100, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(140, 180, 200, 25);
        add(phoneField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 220, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 220, 200, 25);
        add(passwordField);

        saveButton = new JButton("Simpan");
        saveButton.setBounds(140, 270, 90, 30);
        saveButton.addActionListener(this);
        add(saveButton);

        backButton = new JButton("Kembali");
        backButton.setBounds(250, 270, 90, 30);
        backButton.addActionListener(this);
        add(backButton);

        loadClientData();

        setVisible(true);
    }

    private void loadClientData() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT * FROM client WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.clientId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                firstNameField.setText(rs.getString("first_name"));
                lastNameField.setText(rs.getString("last_name"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone_number"));
                passwordField.setText(rs.getString("password"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            updateClientData();
        } else if (e.getSource() == backButton) {
            new ClientDashboard();
            dispose();
        }
    }

    private void updateClientData() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneField.getText();
        String password = new String(passwordField.getPassword());

        if (firstName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name, email, dan password wajib diisi!");
            return;
        }

        try (Connection conn = DatabaseConnection.connect()) {
            String query = "UPDATE client SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, password);
            stmt.setInt(6, Session.clientId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                Session.clientFirstName = firstName; // Update session
                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui data.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kesalahan saat update data: " + ex.getMessage());
        }
    }
}

