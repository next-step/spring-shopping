package shopping.domain.entity;


import shopping.exception.QuantityInvalidException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Quantity {

    public static final Quantity ONE = new Quantity(1);

    private final int quantity;

    protected Quantity() {
        this.quantity = 0;
    }

    public Quantity(final int quantity) {
        validatePositive(quantity);
        this.quantity = quantity;
    }

    private void validatePositive(final int quantity) {
        if (quantity <= 0) {
            throw new QuantityInvalidException();
        }
    }

    public Quantity increase() {
        return new Quantity(quantity + 1);
    }

    public Quantity update(final int quantity) {
        return new Quantity(quantity);
    }

    public int getValue() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
