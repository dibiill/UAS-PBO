package Login;
import ViewCars.ViewAvailCars;
import java.awt.*;
import javax.swing.*;

public class ClientDashboard extends JFrame {

    public ClientDashboard() {
        setTitle("Client Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton viewCarsBtn = new JButton("View Available Cars");
        JButton rentCarBtn = new JButton("Rent a Car");
        JButton myRentsBtn = new JButton("Show My Rentals");
        JButton editProfileBtn = new JButton("Edit My Info");
        JButton changePassBtn = new JButton("Change Password");
        JButton logoutBtn = new JButton("Logout");

        panel.add(viewCarsBtn);
        panel.add(rentCarBtn);
        panel.add(myRentsBtn);
        panel.add(editProfileBtn);
        panel.add(changePassBtn);
        panel.add(logoutBtn);

        add(panel);

        logoutBtn.addActionListener(e -> {
            dispose();
            new MainFrame(); // Kembali ke tampilan awal
        });

        viewCarsBtn.addActionListener(e -> new ViewAvailCars());

        setVisible(true);
    }
}