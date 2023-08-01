package shopping.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.exception.ShoppingException;

@Embeddable
public class ProductPrice {

    private int price;

    protected ProductPrice() {
    }

    public ProductPrice(final int price) {
        validatePriceLessThanEqualZero(price);

        this.price = price;
    }

    private void validatePriceLessThanEqualZero(final int value) {
        if (value <= 0) {
            throw new ShoppingException("상품 가격은 0이하면 안됩니다. 입력값: " + value);
        }
    }

    public int getPrice() {
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
        final ProductPrice that = (ProductPrice) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
