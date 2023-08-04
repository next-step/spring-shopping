package shopping.auth;

public interface PasswordEncoder {

    boolean match(String planePassword, String digest);

    String encode(String password);
}
