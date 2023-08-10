package shopping.domain.order;

import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderPrice {
    public static final long DEFAULT_VALUE = 0;
    public static final long MAX_ORDER_PRICE = 1_000L * 1_000_000_000L;
    @Column(name = "order_price")
    private long value;

    protected OrderPrice() {
    }

    private OrderPrice(final long value) {
        validate(value);
        this.value = value;
    }

    public static OrderPrice from(final long value) {
        return new OrderPrice(value);
    }

    public static OrderPrice defaultValue() {
        return new OrderPrice(DEFAULT_VALUE);
    }

    private void validate(final long value) {
        if (value < 0 || value > MAX_ORDER_PRICE) {
            throw new ShoppingException(ErrorCode.ORDER_PRICE_INVALID);
        }
    }

    public OrderPrice plusPrice(final long orderPrice) {
        return new OrderPrice(this.value + orderPrice);
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderPrice that = (OrderPrice) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
