package CarRental;

public class Session {
    public static int clientId = -1;
    public static String clientFirstName = null;

    public static void setSession(int id, String firstName) {
        clientId = id;
        clientFirstName = firstName;
    }

    public static void clearSession() {
        clientId = -1;
        clientFirstName = null;
    }
}
