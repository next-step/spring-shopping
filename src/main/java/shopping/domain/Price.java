package shopping.domain;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Price {

    private final int price;

    protected Price() {
        this.price = 0;
    }

    public Price(int value) {
        validateIsPositive(value);

        this.price = value;
    }

    private void validateIsPositive(int value) {
        if (value <= 0) {
            throw new ShoppingException(ErrorType.PRICE_INVALID);
        }
    }

    public int getPrice() {
        return price;
    }
}
