package shopping.domain.member;

import static shopping.exception.ShoppingErrorType.NICKNAME_INVALID;

import org.springframework.util.StringUtils;
import shopping.exception.ShoppingException;

public class Nickname {

    private static final int MAX_NICKNAME_LENGTH = 10;

    private final String value;

    private Nickname(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!StringUtils.hasText(value) || value.length() > MAX_NICKNAME_LENGTH) {
            throw new ShoppingException(NICKNAME_INVALID);
        }
    }

    public static Nickname from(final String value) {
        return new Nickname(value);
    }

    public String getValue() {
        return this.value;
    }

}
