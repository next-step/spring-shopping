package shopping.domain;

import shopping.exception.EmailFormInvalidException;
import shopping.exception.EmailLengthInvalidException;

public class Email {

    private static final int MAX_LENGTH = 50;
    private static final String FORM_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final String value;

    protected Email() {
        this.value = null;
    }

    public Email(final String value) {
        validateLength(value);
        validateForm(value);

        this.value = value;
    }

    private void validateLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new EmailLengthInvalidException();
        }
    }

    private void validateForm(final String value) {
        if (!value.matches(FORM_PATTERN)) {
            throw new EmailFormInvalidException();
        }
    }

    public String getValue() {
        return value;
    }
}