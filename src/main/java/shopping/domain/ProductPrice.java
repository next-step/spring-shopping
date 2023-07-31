package shopping.domain;

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
}
