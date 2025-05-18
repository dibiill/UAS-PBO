package Login;

import javax.swing.*;
import java.awt.*;

public class ClientRegister extends JFrame {

    public ClientRegister() {
        setTitle("Register Client");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5)); // Hanya 6 baris input + 1 tombol
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton registerBtn = new JButton("Register");

        // Add form fields
        panel.add(new JLabel("First Name:")); panel.add(firstNameField);
        panel.add(new JLabel("Last Name:")); panel.add(lastNameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);
        panel.add(new JLabel("Password:")); panel.add(passField);
        panel.add(new JLabel()); panel.add(registerBtn);

        add(panel);

        // Action listener
        registerBtn.addActionListener(e -> {
            String email = emailField.getText();

            if (UserDatabase.emailExists(email)) {
                JOptionPane.showMessageDialog(this, "Email already registered!");
            } else {
                int id = UserDatabase.generateNewID(); // ID generator
                User newUser = new User(
                        id,
                        firstNameField.getText(),
                        lastNameField.getText(),
                        email,
                        phoneField.getText(),
                        new String(passField.getPassword())
                );
                UserDatabase.addUser(newUser);
                JOptionPane.showMessageDialog(this, "Registered successfully!");
                dispose();
                new ClientLogin(); // Optional: redirect to login
            }
        });

        setVisible(true);
    }
}
