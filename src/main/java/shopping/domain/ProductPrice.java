package shopping.domain;

import shopping.exception.ShoppingException;

public class ProductPrice {

    private int value;

    public ProductPrice(final int value) {
        validatePriceLessThanEqualZero(value);

        this.value = value;
    }

    private void validatePriceLessThanEqualZero(final int value) {
        if (value <= 0) {
            throw new ShoppingException("상품 가격은 0이하면 안됩니다. 입력값: " + value);
        }
    }

}
