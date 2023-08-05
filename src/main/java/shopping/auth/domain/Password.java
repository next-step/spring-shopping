package shopping.auth.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.ShoppingException;

@Embeddable
public class Password {

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z]).{4,10}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private String value;

    protected Password() {
    }

    public Password(final String value) {
        validatePattern(value);

        this.value = value;
    }

    private static void validatePattern(final String value) {
        final Matcher matcher = PASSWORD_PATTERN.matcher(value);

        if (!matcher.matches()) {
            throw new ShoppingException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
