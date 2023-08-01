package shopping.domain.member;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Embeddable
public class Email {

    private static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Column(name = "email", length = 40)
    private String value;

    protected Email() {
    }

    private Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || !value.matches(EMAIL_REGEX_PATTERN)) {
            throw new ShoppingException(ErrorCode.EMAIL_INVALID);
        }
    }

    public static Email from(final String value) {
        return new Email(value);
    }

    public String getValue() {
        return this.value;
    }
}
