package shopping.domain.cart;

import java.util.Objects;
import shopping.exception.ExceptionType;
import shopping.exception.ShoppingException;

public class CartProductQuantity {

    private final int value;

    protected CartProductQuantity() {
        this.value = 0;
    }

    public CartProductQuantity(final int value) {
        validateCountLessThanEqualZero(value);

        this.value = value;
    }

    private void validateCountLessThanEqualZero(final int value) {
        if (value <= 0) {
            throw new ShoppingException(ExceptionType.INVALID_QUANTITY_SIZE, value);
        }
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
        final CartProductQuantity that = (CartProductQuantity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
