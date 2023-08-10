package shopping.domain.order;

import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderItemQuantity {

    private static final int MIN_QUANTITY = 1;

    private static final int MAX_QUANTITY = 1000;

    @Column(name = "order_item_quantity", nullable = false)
    private int value;

    protected OrderItemQuantity() {
    }

    private OrderItemQuantity(final int value) {
        validate(value);
        this.value = value;
    }

    public static OrderItemQuantity from(final int value) {
        return new OrderItemQuantity(value);
    }

    private void validate(final int value) {
        if (value < MIN_QUANTITY || value > MAX_QUANTITY) {
            throw new ShoppingException(ErrorCode.ORDER_ITEM_QUANTITY_INVALID);
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderItemQuantity that = (OrderItemQuantity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
