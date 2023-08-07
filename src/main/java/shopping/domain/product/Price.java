package shopping.domain.product;

import static shopping.exception.ShoppingErrorType.PRICE_LESS_THAN_ZERO;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import shopping.exception.ShoppingException;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private int value;

    protected Price() {
    }

    private Price(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value <= 0) {
            throw new ShoppingException(PRICE_LESS_THAN_ZERO);
        }
    }

    public static Price from(int value) {
        return new Price(value);
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
        final Price price = (Price) o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
