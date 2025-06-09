package CarRental;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class LoginMain {
    public LoginMain() {
        JFrame frame = new JFrame("Login Menu");
        frame.setSize(370, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 255));
        mainPanel.setLayout(null);

        JLabel label = new JLabel("Login as", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(new Color(44, 62, 80));
        label.setBounds(60, 30, 250, 36);
        mainPanel.add(label);

        int buttonWidth = 170;
        int buttonHeight = 38;
        int startX = (370 - buttonWidth) / 2;

        JButton adminBtn = createModernButton("Admin", new Color(100, 149, 237)); // Soft blue
        adminBtn.setBounds(startX, 90, buttonWidth, buttonHeight);
        mainPanel.add(adminBtn);

        JButton clientBtn = createModernButton("Client", new Color(46, 204, 113)); // Soft green
        clientBtn.setBounds(startX, 145, buttonWidth, buttonHeight);
        mainPanel.add(clientBtn);

        JButton backBtn = createModernButton("Back", new Color(220, 87, 18)); // Soft orange-red
        backBtn.setBounds(startX, 200, buttonWidth, buttonHeight);
        mainPanel.add(backBtn);

        adminBtn.addActionListener(e -> {
            new AdminLogin();
            frame.dispose();
        });

        clientBtn.addActionListener(e -> {
            new ClientLogin();
            frame.dispose();
        });

        backBtn.addActionListener(e -> {
            new RegisterMain();
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
                BorderFactory.createEmptyBorder(7, 24, 7, 24)
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

