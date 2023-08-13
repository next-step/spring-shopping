package shopping.domain.order;

import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderItemPrice {
    public static final int DEFAULT_VALUE = 0;

    public static final int MAX_PRICE = 1_000_000_000;

    @Column(name = "order_item_price", nullable = false)
    private int value;

    protected OrderItemPrice() {
    }

    private OrderItemPrice(final int value) {
        validate(value);
        this.value = value;
    }

    public static OrderItemPrice from(final int value) {
        return new OrderItemPrice(value);
    }

    private void validate(final int value) {
        if (value < DEFAULT_VALUE || value > MAX_PRICE) {
            throw new ShoppingException(ErrorCode.PRICE_INVALID);
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderItemPrice that = (OrderItemPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
