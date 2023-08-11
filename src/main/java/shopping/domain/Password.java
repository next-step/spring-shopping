package shopping.domain;

import shopping.infrastructure.PasswordEncoder;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
