package shopping.cart.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.global.exception.ShoppingException;

@Embeddable
public class Price {

    private long price;

    protected Price() {
    }

    public Price(final long price) {
        validatePriceLessThanEqualZero(price);

        this.price = price;
    }

    private void validatePriceLessThanEqualZero(final long value) {
        if (value <= 0) {
            throw new ShoppingException("상품 가격은 0이하면 안됩니다. 입력값: " + value);
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
        final Price that = (Price) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
