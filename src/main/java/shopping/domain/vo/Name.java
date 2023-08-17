package shopping.domain.vo;

import shopping.exception.NameLengthInvalidException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    private static final int MAX_LENGTH = 20;

    private final String value;

    protected Name() {
        this.value = null;
    }

    public Name(final String value) {
        validateLength(value);

        this.value = value;
    }

    private void validateLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new NameLengthInvalidException();
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name1 = (Name) o;
        return Objects.equals(value, name1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
