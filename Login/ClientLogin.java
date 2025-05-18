package Login;
import java.awt.*;
import javax.swing.*;

public class ClientLogin extends JFrame {
   public ClientLogin() {
        setTitle("Client Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passField.getPassword());

            User user = UserDatabase.authenticate(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Success! Welcome " + user.getFirstName());
                dispose();
                new ClientDashboard(); 
            } else {
                JOptionPane.showMessageDialog(this, "Login failed.");
            }
        });

        setVisible(true);
    }
}

