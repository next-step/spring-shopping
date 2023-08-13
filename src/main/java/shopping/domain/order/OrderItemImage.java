package shopping.domain.order;

import org.springframework.util.StringUtils;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderItemImage {

    private static final int MAX_IMAGE_LENGTH = 255;

    @Column(name = "image", nullable = false)
    private String value;

    protected OrderItemImage() {
    }

    private OrderItemImage(final String value) {
        validate(value);
        this.value = value;
    }

    public static OrderItemImage from(final String value) {
        return new OrderItemImage(value);
    }

    private void validate(final String value) {
        if (!StringUtils.hasText(value) || value.length() > MAX_IMAGE_LENGTH) {
            throw new ShoppingException(ErrorCode.ORDER_ITEM_IMAGE_INVALID);
        }
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
