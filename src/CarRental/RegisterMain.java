package CarRental;

import javax.swing.*;

public class RegisterMain {
    public static void main(String[] args) {
        new RegisterMain();
    }

    public RegisterMain() {
        JFrame frame = new JFrame("Register Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel title = new JLabel("Welcome to Car Rental System");
        title.setBounds(107, 20, 250, 30);
        frame.add(title);

        JButton registerClientBtn = new JButton("Register Client");
        registerClientBtn.setBounds(120, 70, 150, 30);
        frame.add(registerClientBtn);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 110, 150, 30);
        frame.add(loginBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(120, 170, 150, 30);
        frame.add(exitBtn);


        registerClientBtn.addActionListener(e -> {
            new ClientRegister();
            frame.dispose();
        });

        loginBtn.addActionListener(e -> {
            new LoginMain(); 
            frame.dispose();
        });

        exitBtn.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}

