package CarRental;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class ClientRegister {
    public ClientRegister() {
        JFrame frame = new JFrame("Register Client");
        frame.setSize(420, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 255));
        mainPanel.setLayout(null);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Client Registration", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 19));
        title.setForeground(new Color(44, 62, 80));
        title.setBounds(40, 18, 320, 28);
        mainPanel.add(title);

        // First Name
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        firstNameLabel.setBounds(30, 60, 100, 22);
        mainPanel.add(firstNameLabel);

        JTextField firstNameText = new JTextField();
        firstNameText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        firstNameText.setBounds(140, 60, 220, 30);
        mainPanel.add(firstNameText);

        // Last Name
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lastNameLabel.setBounds(30, 100, 100, 22);
        mainPanel.add(lastNameLabel);

        JTextField lastNameText = new JTextField();
        lastNameText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lastNameText.setBounds(140, 100, 220, 30);
        mainPanel.add(lastNameText);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        emailLabel.setBounds(30, 140, 100, 22);
        mainPanel.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        emailText.setBounds(140, 140, 220, 30);
        mainPanel.add(emailText);

        // Phone Number
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        phoneLabel.setBounds(30, 180, 100, 22);
        mainPanel.add(phoneLabel);

        JTextField phoneText = new JTextField();
        phoneText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        phoneText.setBounds(140, 180, 220, 30);
        mainPanel.add(phoneText);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passLabel.setBounds(30, 220, 100, 22);
        mainPanel.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passText.setBounds(140, 220, 220, 30);
        mainPanel.add(passText);

        // Register & Back button (lebar sama, rata tengah, jarak antar tombol)
        int buttonWidth = 120;
        int buttonHeight = 38;
        int gap = 18;
        int totalWidth = buttonWidth * 2 + gap;
        int startX = (420 - totalWidth) / 2;

        JButton registerBtn = createModernButton("Register", new Color(100, 149, 237)); // Soft blue
        registerBtn.setBounds(startX, 270, buttonWidth, buttonHeight);
        mainPanel.add(registerBtn);

        JButton backBtn = createModernButton("Back", new Color(220, 87, 18)); // Soft orange
        backBtn.setBounds(startX + buttonWidth + gap, 270, buttonWidth, buttonHeight);
        mainPanel.add(backBtn);

        // Action: Register
        registerBtn.addActionListener(e -> {
            String firstName = firstNameText.getText().trim();
            String lastName = lastNameText.getText().trim();
            String email = emailText.getText().trim();
            String phone = phoneText.getText().trim();
            String password = new String(passText.getPassword());

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua data harus diisi!");
                return;
            }

            // Validasi email sederhana
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                JOptionPane.showMessageDialog(frame, "Format email tidak valid!");
                return;
            }

            try (Connection conn = DatabaseConnection.connect()) {
                // Cek email unik
                String cekEmail = "SELECT id FROM client WHERE email = ?";
                PreparedStatement cekStmt = conn.prepareStatement(cekEmail);
                cekStmt.setString(1, email);
                ResultSet rs = cekStmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame, "Email sudah terdaftar!");
                    return;
                }

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

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    // Membuat tombol dengan efek hover dan rounded
    private JButton createModernButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setUI(new BasicButtonUI());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }
}
