package CarRental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/db_car_rental";
            String user = "root"; 
            String password = ""; 
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi Berhasil!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal!");
            e.printStackTrace();
            return null;
        }
    }
}
