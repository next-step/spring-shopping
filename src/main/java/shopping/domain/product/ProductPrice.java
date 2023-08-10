package shopping.domain.product;

import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    public static final int DEFAULT_VALUE = 0;
    public static final int MAX_PRICE = 1_000_000_000;
    @Column(name = "product_price", nullable = false)
    private int value;

    protected ProductPrice() {
    }

    private ProductPrice(final int value) {
        validate(value);
        this.value = value;
    }

    public static ProductPrice from(int value) {
        return new ProductPrice(value);
    }

    private void validate(final int value) {
        if (value < DEFAULT_VALUE || value > MAX_PRICE) {
            throw new ShoppingException(ErrorCode.PRICE_INVALID);
        }
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductPrice productPrice = (ProductPrice) o;
        return value == productPrice.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
