package shopping.domain.user;

import javax.persistence.Embeddable;

@Embeddable
public class Password {
    private final String password;

    protected Password() {
        this.password = null;
    }

    private Password(final String password) {
        this.password = password;
    }

    public static Password createEncodedPassword(final String password, final PasswordEncoder encoder) {
        return new Password(encoder.encode(password));
    }

    public String getPassword() {
        return password;
    }
}
