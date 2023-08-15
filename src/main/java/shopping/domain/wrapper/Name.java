package shopping.domain.wrapper;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
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
            throw new ShoppingException(ErrorType.NAME_TOO_LONG);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
