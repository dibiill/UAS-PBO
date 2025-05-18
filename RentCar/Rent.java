package RentCar;

import ViewCars.Car;
import Login.User;

public class Rent {
    private int id;
    private User user;
    private Car car;
    private String dateTime;
    private int hours;
    private double total;
    private int status; // 0 = Ongoing, 1 = Completed, 2 = Late

    public Rent(int id, User user, Car car, String dateTime, int hours, double total, int status) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.dateTime = dateTime;
        this.hours = hours;
        this.total = total;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getHours() {
        return hours;
    }

    public double getTotal() {
        return total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusToString() {
        return switch (status) {
            case 0 -> "Ongoing";
            case 1 -> "Completed";
            case 2 -> "Late";
            default -> "Unknown";
        };
    }

    public int getDelayedHours() {
        // Dummy logic: you can connect to system time in real app
        return Math.max(0, hours - 24); // Misalnya seharusnya 24 jam
    }
} 