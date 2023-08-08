package shopping.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderItemQuantity {
    @Column(name = "quantity", nullable = false)
    private int value;

    protected OrderItemQuantity() {
    }

    private OrderItemQuantity(final int value) {
        this.value = value;
    }

    public static OrderItemQuantity from(final int value) {
        return new OrderItemQuantity(value);
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
