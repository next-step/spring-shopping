package shopping.domain.product;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    private final int price;

    protected ProductPrice() {
        this.price = 0;
    }

    public ProductPrice(final int value) {
        validateIsPositive(value);

        this.price = value;
    }

    private void validateIsPositive(final int value) {
        if (value <= 0) {
            throw new ShoppingException(ErrorType.PRICE_INVALID);
        }
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice price1 = (ProductPrice) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
