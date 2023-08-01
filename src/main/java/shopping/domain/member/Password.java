package shopping.domain.member;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Embeddable
public class Password {

    private static final String PASSWORD_REGEX_PATTERN = "^(?=.*[a-z])(?=.*[!@#$%^&*])(?=.{7,18}$).*";

    @Column(name = "password", length = 18)
    private String value;

    protected Password() {
    }

    private Password(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || !value.matches(PASSWORD_REGEX_PATTERN)) {
            throw new ShoppingException(ErrorCode.PASSWORD_INVALID);
        }
    }

    public static Password from(final String value) {
        return new Password(value);
    }

    public boolean isMatch(final Password password) {
        return this.equals(password);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
