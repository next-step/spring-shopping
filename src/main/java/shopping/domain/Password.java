package shopping.domain;

import shopping.infrastructure.PasswordEncoder;

public class Password {

    private final String value;

    protected Password() {
        this.value = null;
    }

    private Password(final String value) {
        this.value = value;
    }

    public static Password createEncodedPassword(final String password, final PasswordEncoder encoder) {
        return new Password(encoder.encode(password));
    }

    public String getValue() {
        return value;
    }
}
