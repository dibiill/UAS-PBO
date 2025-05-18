package ViewCars;
import java.util.ArrayList;
import javax.swing.*;


public class ViewAvailCars extends JFrame {
    public ViewAvailCars() {
        setTitle("Available Cars");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArrayList<Car> carList = getDummyCars();

        String[] columnNames = { "Plate Number", "Brand", "Model", "Available" };
        String[][] data = new String[carList.size()][4];

        for (int i = 0; i < carList.size(); i++) {
            Car car = carList.get(i);
            data[i][0] = car.getPlateNumber();
            data[i][1] = car.getBrand();
            data[i][2] = car.getModel();
            data[i][3] = car.isAvailable() ? "Yes" : "No";
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }

    // Dummy data
    private ArrayList<Car> getDummyCars() {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car("AB 2341 JK", "Toyota", "Avanza", "White", 2020, 300000.0, true));
        cars.add(new Car("B 3942 KML", "Honda", "Civic", "Black", 2019, 400000.0, false));
        cars.add(new Car("T 2398 RIU", "Suzuki", "Ertiga", "Silver", 2021, 350000.0, true));
        cars.add(new Car("B 3091 DNA", "Hyundai", "Palisade", "Black", 2023, 320000.0, true));
        cars.add(new Car("K 1309 NDY", "Nissan", "Livina", "Blue", 2023, 450000.0, false));

        return cars;
    }
    
}
