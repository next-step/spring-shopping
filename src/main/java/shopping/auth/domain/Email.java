package shopping.auth.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.ShoppingException;

@Embeddable
public class Email {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private String value;

    protected Email() {
    }

    public Email(final String value) {
        validatePattern(value);

        this.value = value;
    }

    private static void validatePattern(final String value) {
        final Matcher matcher = EMAIL_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new ShoppingException(ErrorCode.INVALID_EMAIL);
        }
    }
}
