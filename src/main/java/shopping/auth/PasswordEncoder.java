package shopping.auth;

import shopping.domain.user.Password;

public interface PasswordEncoder {

    boolean match(String planePassword, Password digest);
    String encode(String password);
}
