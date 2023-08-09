package shopping.domain.wrapper;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
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
        Price price1 = (Price) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
