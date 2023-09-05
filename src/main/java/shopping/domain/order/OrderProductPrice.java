package shopping.domain.order;

import static shopping.exception.OrderExceptionType.INVALID_PRICE_SIZE;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.exception.ShoppingException;

@Embeddable
public class OrderProductPrice {

    private long price;

    protected OrderProductPrice() {
    }

    public OrderProductPrice(final long price) {
        validatePriceLessThanEqualZero(price);

        this.price = price;
    }

    private void validatePriceLessThanEqualZero(final long value) {
        if (value <= 0) {
            throw new ShoppingException(INVALID_PRICE_SIZE, value);
        }
    }

    public long getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderProductPrice that = (OrderProductPrice) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
