package shopping.domain.vo;


import shopping.exception.QuantityInvalidException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Quantity {

    public static final Quantity ONE = new Quantity(1);

    private final int value;

    protected Quantity() {
        this.value = 0;
    }

    public Quantity(final int value) {
        validatePositive(value);
        this.value = value;
    }

    private void validatePositive(final int quantity) {
        if (quantity <= 0) {
            throw new QuantityInvalidException();
        }
    }

    public Quantity increase() {
        return new Quantity(value + 1);
    }

    public Quantity update(final int quantity) {
        return new Quantity(quantity);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return value == quantity1.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
