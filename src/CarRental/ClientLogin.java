package CarRental;

import CarRental.Client.ClientDashboard;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class ClientLogin {
    public ClientLogin() {
        JFrame frame = new JFrame("Client Login");
        frame.setSize(370, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 255));
        mainPanel.setLayout(null);

        JLabel title = new JLabel("Client Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(44, 62, 80));
        title.setBounds(0, 18, 370, 28);
        mainPanel.add(title);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        emailLabel.setBounds(40, 60, 80, 22);
        mainPanel.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        emailText.setBounds(130, 60, 200, 26);
        mainPanel.add(emailText);

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
            String email = emailText.getText();
            String password = new String(passText.getPassword());

            try (Connection conn = DatabaseConnection.connect()) {
                String sql = "SELECT id, first_name FROM client WHERE email=? AND password=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int clientId = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    Session.setSession(clientId, firstName);
                    new ClientDashboard(clientId);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Email atau Password salah!");
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

