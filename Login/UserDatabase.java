package Login;

import java.util.ArrayList;

public class UserDatabase {
    private static ArrayList<User> users = new ArrayList<>();
    private static int currentID = 1;

    public static void addUser(User user) {
        users.add(user);
    }

    public static User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean emailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) return true;
        }
        return false;
    }

    public static int generateNewID() {
        return currentID++;
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }
}
