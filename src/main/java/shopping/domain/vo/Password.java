package shopping.domain.vo;

import shopping.infrastructure.PasswordEncoder;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Password {

    private String value;

    protected Password() {
        this.value = null;
    }

    private Password(final String value) {
        this.value = value;
    }

    public static Password from(final String value) {
        return new Password(value);
    }

    public void encode(final PasswordEncoder encoder) {
        this.value = encoder.encode(value);
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
