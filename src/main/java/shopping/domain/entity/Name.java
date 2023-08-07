package shopping.domain.entity;

import shopping.exception.NameLengthInvalidException;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private static final int MAX_LENGTH = 20;

    private final String name;

    protected Name() {
        this.name = null;
    }

    public Name(final String value) {
        validateLength(value);

        this.name = value;
    }

    private void validateLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new NameLengthInvalidException();
        }
    }

    public String getName() {
        return name;
    }
}
