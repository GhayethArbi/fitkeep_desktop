package services.session;

public class UserSession {
    public static UserSession CURRENT_USER;
    private AuthDTO user;

    public UserSession(AuthDTO userLoggedIn) {
        this.user = userLoggedIn;
    }

    public static UserSession getSession(AuthDTO userLoggedIn) {
        if(CURRENT_USER == null) {
            CURRENT_USER = new UserSession(userLoggedIn);
        }
        return CURRENT_USER;
    }

    public AuthDTO getUserLoggedIn() {
        return user;
    }
    public void logout() {
        this.user=null;
        CURRENT_USER = null;
        System.out.println("go to login");
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userLoggedIn=" + user +
                '}';
    }
}
