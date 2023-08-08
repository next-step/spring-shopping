package shopping.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderItemImage {

    @Column(name = "image", nullable = false)
    private String value;

    protected OrderItemImage() {
    }

    private OrderItemImage(final String value) {
        this.value = value;
    }

    public static OrderItemImage from(final String value) {
        return new OrderItemImage(value);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderItemImage that = (OrderItemImage) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
