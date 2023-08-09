package shopping.domain.entity;

import shopping.exception.NameLengthInvalidException;

import javax.persistence.Embeddable;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
