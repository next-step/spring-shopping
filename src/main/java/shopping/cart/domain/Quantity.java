package shopping.cart.domain;

import javax.persistence.Embeddable;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.ShoppingException;

@Embeddable
public class Quantity {

    private static final int ZERO = 0;
    private static final int MAX_QUANTITY = 1000;

    private int value;

    protected Quantity() {
    }

    public Quantity(final int value) {
        validateRange(value);
        this.value = value;
    }

    public boolean isZero() {
        return this.value == ZERO;
    }

    private static void validateRange(final int value) {
        if (value < ZERO || MAX_QUANTITY < value) {
            throw new ShoppingException(ErrorCode.INVALID_QUANTITY);
        }
    }
}
