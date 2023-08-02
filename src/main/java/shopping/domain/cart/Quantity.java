package shopping.domain.cart;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingApiException;

@Embeddable
public class Quantity {

    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 1000;

    @Column(name = "quantity", nullable = false)
    private int value;

    protected Quantity() {
    }

    private Quantity(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < MIN_QUANTITY || value > MAX_QUANTITY) {
            throw new ShoppingApiException(ErrorCode.QUANTITY_INVALID);
        }
    }

    public static Quantity from(final int value) {
        return new Quantity(value);
    }

    public static Quantity defaultValue() {
        return new Quantity(MIN_QUANTITY);
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
