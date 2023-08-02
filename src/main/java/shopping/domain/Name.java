package shopping.domain;

import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private static final int MAX_LENGTH = 20;
    
    private final String value;

    protected Name() {
        this.value = null;
    }

    public Name(String value) {
        validateLength(value);

        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ShoppingException("이름은 20자를 넘을 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
