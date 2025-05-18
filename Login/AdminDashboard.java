package Login;
import java.awt.*;
import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton addCarBtn = new JButton("Add New Car");
        JButton viewRentsBtn = new JButton("Show All Rents");
        JButton updateCarBtn = new JButton("Update Car");
        JButton logoutBtn = new JButton("Logout");

        panel.add(addCarBtn);
        panel.add(viewRentsBtn);
        panel.add(updateCarBtn);
        panel.add(new JLabel());
        panel.add(logoutBtn);

        add(panel);

        logoutBtn.addActionListener(e -> {
            dispose();
            new MainFrame(); // kembali ke menu utama
        });

        setVisible(true);
    }
}

