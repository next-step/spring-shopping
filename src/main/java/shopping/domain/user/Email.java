package shopping.domain.user;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Email {

    private static final int MAX_LENGTH = 50;
    private static final String FORM_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final String email;

    protected Email() {
        this.email = null;
    }

    public Email(final String value) {
        validateLength(value);
        validateForm(value);

        this.email = value;
    }

    private void validateLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ShoppingException(ErrorType.EMAIL_TOO_LONG);
        }
    }

    private void validateForm(final String value) {
        if (!value.matches(FORM_PATTERN)) {
            throw new ShoppingException(ErrorType.EMAIL_INVALID_FORM);
        }
    }

    public String getEmail() {
        return email;
    }
}
