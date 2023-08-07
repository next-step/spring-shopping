package shopping.domain.member;

import static shopping.exception.ShoppingErrorType.EMAIL_INVALID;

import shopping.exception.ShoppingException;

public class Email {

    private static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final String value;

    private Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || !value.matches(EMAIL_REGEX_PATTERN)) {
            throw new ShoppingException(EMAIL_INVALID);
        }
    }

    public static Email from(final String value) {
        return new Email(value);
    }

    public String getValue() {
        return this.value;
    }

}
