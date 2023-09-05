package shopping.domain.order;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.exception.OrderExceptionType;
import shopping.exception.ShoppingException;

@Embeddable
public class OrderProductQuantity {

    private static final int MIN_QUANTITY = 1;

    private final int quantity;

    protected OrderProductQuantity() {
        this.quantity = MIN_QUANTITY;
    }

    public OrderProductQuantity(final int quantity) {
        validateValueIsNotLessThanOne(quantity);

        this.quantity = quantity;
    }

    private void validateValueIsNotLessThanOne(final int value) {
        if (value < MIN_QUANTITY) {
            throw new ShoppingException(OrderExceptionType.INVALID_QUANTITY_SIZE, value);
        }
    }

    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderProductQuantity that = (OrderProductQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
