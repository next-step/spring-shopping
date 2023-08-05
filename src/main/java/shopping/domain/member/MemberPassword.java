package shopping.domain.member;

import java.util.Objects;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ExceptionType;
import shopping.exception.ShoppingException;

@Embeddable
public class MemberPassword {

    private static final int MAX_LENGTH = 30;

    private String value;

    protected MemberPassword() {
    }

    public MemberPassword(final String value) {
        validateIsNotNullOrEmpty(value);
        validateGreaterThanMaxLength(value);

        this.value = value;
    }

    private void validateGreaterThanMaxLength(final String password) {
        if (password.length() > MAX_LENGTH) {
            throw new ShoppingException(ExceptionType.INVALID_PASSWORD_LENGTH, password);
        }
    }

    private void validateIsNotNullOrEmpty(final String password) {
        if (!StringUtils.hasText(password)) {
            throw new ShoppingException(ExceptionType.NO_CONTENT_PASSWORD, password);
        }
    }

    public boolean match(final String password) {
        return this.value.equals(password);
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
        final MemberPassword that = (MemberPassword) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
