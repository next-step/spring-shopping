package shopping.domain.entity;

import shopping.exception.PriceInvalidException;

import javax.persistence.Embeddable;

@Embeddable
public class Price {

    private final int price;

    protected Price() {
        this.price = 0;
    }

    public Price(final int value) {
        validateIsPositive(value);

        this.price = value;
    }

    private void validateIsPositive(final int value) {
        if (value <= 0) {
            throw new PriceInvalidException();
        }
    }

    public int getPrice() {
        return price;
    }
}
