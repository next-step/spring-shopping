package shopping.domain;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private static final int MAX_LENGTH = 20;

    private final String name;

    protected Name() {
        this.name = null;
    }

    public Name(String value) {
        validateLength(value);

        this.name = value;
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ShoppingException(ErrorType.NAME_TOO_LONG);
        }
    }

    public String getName() {
        return name;
    }
}
