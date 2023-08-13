package shopping.domain.order;

import org.springframework.util.StringUtils;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderItemName {

    private static final int MAX_ORDER_ITEM_NAME_LENGTH = 30;

    @Column(name = "name", nullable = false, length = MAX_ORDER_ITEM_NAME_LENGTH)
    private String value;

    protected OrderItemName() {
    }

    private OrderItemName(final String value) {
        validate(value);
        this.value = value;
    }

    public static OrderItemName from(final String value) {
        return new OrderItemName(value);
    }

    private void validate(final String value) {
        if (!StringUtils.hasText(value) || value.length() > MAX_ORDER_ITEM_NAME_LENGTH) {
            throw new ShoppingException(ErrorCode.ORDER_ITEM_NAME_INVALID);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderItemName that = (OrderItemName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
