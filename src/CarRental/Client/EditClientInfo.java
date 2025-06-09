package CarRental.Client;

import CarRental.DatabaseConnection;
import CarRental.Session;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class EditClientInfo extends JFrame implements ActionListener {
    private JTextField firstNameField, lastNameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JButton saveButton, backButton;

    public EditClientInfo() {
        setTitle("Edit Profil");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 255));
        setLayout(null);

        JLabel title = new JLabel("Edit Informasi Client", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBounds(60, 10, 280, 30);
        add(title);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        firstNameLabel.setBounds(30, 60, 100, 25);
        add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        firstNameField.setBounds(140, 60, 200, 25);
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lastNameLabel.setBounds(30, 100, 100, 25);
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lastNameField.setBounds(140, 100, 200, 25);
        add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailLabel.setBounds(30, 140, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField.setBounds(140, 140, 200, 25);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        phoneLabel.setBounds(30, 180, 100, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        phoneField.setBounds(140, 180, 200, 25);
        add(phoneField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setBounds(30, 220, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBounds(140, 220, 200, 25);
        add(passwordField);

        // Panel tombol rata tengah
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 255));
        buttonPanel.setBounds(0, 270, 400, 50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        saveButton = new JButton("Simpan");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        saveButton.setBackground(new Color(39, 174, 96));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(this);

        backButton = new JButton("Kembali");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(100, 149, 237));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(7, 24, 7, 24));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);
        add(buttonPanel);

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
            new ClientDashboard(Session.clientId);
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

