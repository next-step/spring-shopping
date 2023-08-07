package shopping.auth;

public interface PasswordEncoder {

    boolean match(String planePassword, String encodedPassword);
    String encode(String password);
}
