package shopping.domain.user;

import shopping.infrastructure.PasswordEncoder;

import javax.persistence.Embeddable;

@Embeddable
public class Password {
    private final String password;

    protected Password() {
        this.password = null;
    }

    public Password(String password) {
        this.password = password;
    }

    public static Password createEncodedPassword(String password, PasswordEncoder encoder) {
        return new Password(encoder.encode(password));
    }

    public String getPassword() {
        return password;
    }
}
