package shopping.domain.member;

import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ExceptionType;
import shopping.exception.ShoppingException;

@Embeddable
public class MemberEmail {

    private static final Pattern emailPattern = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private String value;

    protected MemberEmail() {
    }

    public MemberEmail(final String value) {
        validateIsNotNullOrEmpty(value);
        validateEmailFormat(value);

        this.value = value;
    }

    private void validateIsNotNullOrEmpty(final String email) {
        if (!StringUtils.hasText(email)) {
            throw new ShoppingException(ExceptionType.NO_CONTENT_EMAIL, email);
        }
    }

    private void validateEmailFormat(final String email) {
        if (!emailPattern.matcher(email).matches()) {
            throw new ShoppingException(ExceptionType.WRONG_EMAIL_FORMAT, email);
        }
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
        final MemberEmail that = (MemberEmail) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
