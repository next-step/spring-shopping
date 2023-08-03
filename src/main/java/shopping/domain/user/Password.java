package shopping.domain.user;

import shopping.infrastructure.PasswordEncoder;

import javax.persistence.Embeddable;

@Embeddable
public class Password {
    private final String password;

    protected Password() {
        this.password = null;
    }

    public Password(final String password) {
        this.password = password;
    }

    public static Password createEncodedPassword(final String password, final PasswordEncoder encoder) {
        return new Password(encoder.encode(password));
    }

    public String getPassword() {
        return password;
    }
}
