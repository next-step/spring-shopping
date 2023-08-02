package shopping.domain;

import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Email {

    private static final int MAX_LENGTH = 50;
    private static final String FORM_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final String value;

    protected Email() {
        this.value = null;
    }

    public Email(String value) {
        validateLength(value);
        validateForm(value);

        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ShoppingException("이메일 길이는 50자를 넘을 수 없습니다.");
        }
    }

    private void validateForm(String value) {
        if (!value.matches(FORM_PATTERN)) {
            throw new ShoppingException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
