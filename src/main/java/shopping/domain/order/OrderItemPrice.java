package shopping.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderItemPrice {

    @Column(name = "price", nullable = false)
    private int value;

    protected OrderItemPrice() {
    }

    private OrderItemPrice(final int value) {
        this.value = value;
    }

    public static OrderItemPrice from(final int value) {
        return new OrderItemPrice(value);
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
