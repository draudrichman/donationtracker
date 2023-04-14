package login;

public class SessionManager {
    private static Integer currentUser;

    public static void setCurrentUser(int userID) {
        currentUser = userID;
    }

    public static int getCurrentUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
