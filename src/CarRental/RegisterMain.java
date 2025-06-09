package CarRental;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class RegisterMain {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        new RegisterMain();
    }

    public RegisterMain() {
        JFrame frame = new JFrame("Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 380);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 245, 255));
        mainPanel.setLayout(null);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Welcome to Car Rental System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(44, 62, 80));
        title.setBounds(50, 30, 400, 32);
        mainPanel.add(title);

        JLabel desc = new JLabel("<html><div style='text-align: center;'>Easy, Fast, and Reliable Car Rental<br>for Your Everyday Needs</div></html>", SwingConstants.CENTER);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        desc.setForeground(new Color(100, 100, 120));
        desc.setBounds(50, 65, 400, 40);
        mainPanel.add(desc);

        int buttonWidth = 200;
        int buttonHeight = 38;
        int startX = (500 - buttonWidth) / 2;

        JButton registerClientBtn = new JButton("Register Client");
        registerClientBtn.setBounds(startX, 130, buttonWidth, buttonHeight);
        styleSoftButton(registerClientBtn, new Color(52, 152, 219), Color.WHITE);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(startX, 185, buttonWidth, buttonHeight);
        styleSoftButton(loginBtn, new Color(39, 174, 96), Color.WHITE);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(startX, 240, buttonWidth, buttonHeight);
        styleSoftButton(exitBtn, new Color(231, 76, 60), Color.WHITE);

        mainPanel.add(registerClientBtn);
        mainPanel.add(loginBtn);
        mainPanel.add(exitBtn);

        registerClientBtn.addActionListener(e -> {
            new ClientRegister();
            frame.dispose();
        });

        loginBtn.addActionListener(e -> {
            new LoginMain();
            frame.dispose();
        });

        exitBtn.addActionListener(e -> frame.dispose());

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    // Tombol dengan warna soft dan hover effect
    private void styleSoftButton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setUI(new BasicButtonUI());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1, true),
                BorderFactory.createEmptyBorder(6, 18, 6, 18)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
    }
}