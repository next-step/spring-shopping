package shopping.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderPrice {
    public static final int DEFAULT_VALUE = 0;
    @Column(name = "order_price")
    private int value;

    protected OrderPrice() {
    }

    private OrderPrice(final int value) {
        this.value = value;
    }

    public static OrderPrice from(final int value) {
        return new OrderPrice(value);
    }

    public static OrderPrice defaultValue() {
        return new OrderPrice(DEFAULT_VALUE);
    }

    public OrderPrice plusPrice(final int orderPrice) {
        return new OrderPrice(this.value + orderPrice);
    }

    public int getValue() {
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
