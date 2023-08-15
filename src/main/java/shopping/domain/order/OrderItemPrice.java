package shopping.domain.order;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class OrderItemPrice {

    private final int price;

    protected OrderItemPrice() {
        this.price = 0;
    }

    public OrderItemPrice(final int value) {
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
        OrderItemPrice price1 = (OrderItemPrice) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
