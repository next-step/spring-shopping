package shopping.auth;

public class PasswordUtil {

    public static boolean match(String password, String password1) {
        return password.equals(password1);
    }
}
