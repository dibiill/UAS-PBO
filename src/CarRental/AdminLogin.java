package CarRental;

import CarRental.Admin.AdminDashboard;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class AdminLogin {
    public AdminLogin() {
        JFrame frame = new JFrame("Admin Login");
        frame.setSize(370, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 255));
        mainPanel.setLayout(null);

        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(44, 62, 80));
        title.setBounds(0, 18, 370, 28);
        mainPanel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        userLabel.setBounds(40, 60, 80, 22);
        mainPanel.add(userLabel);

        JTextField userText = new JTextField();
        userText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        userText.setBounds(130, 60, 200, 26);
        mainPanel.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passLabel.setBounds(40, 100, 80, 22);
        mainPanel.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passText.setBounds(130, 100, 200, 26);
        mainPanel.add(passText);

        int buttonWidth = 100;
        int buttonHeight = 38;
        int gap = 20;
        int totalWidth = buttonWidth * 2 + gap;
        int startX = (370 - totalWidth) / 2;

        JButton loginButton = createModernButton("Login", new Color(100, 149, 237)); // Soft blue
        loginButton.setBounds(startX, 150, buttonWidth, buttonHeight);
        mainPanel.add(loginButton);

        JButton backBtn = createModernButton("Back", new Color(220, 87, 18)); // Soft orange
        backBtn.setBounds(startX + buttonWidth + gap, 150, buttonWidth, buttonHeight);
        mainPanel.add(backBtn);

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
                JOptionPane.showMessageDialog(frame, "Koneksi database gagal.");
            }
        });

        backBtn.addActionListener(e -> {
            new LoginMain();
            frame.dispose();
        });

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    // Modern button with rounded border and hover effect
    private JButton createModernButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setUI(new BasicButtonUI());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1, true),
                BorderFactory.createEmptyBorder(7, 18, 7, 18)
        ));

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

