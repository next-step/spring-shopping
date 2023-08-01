package shopping.domain;


import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Quantity {
    private final int quantity;

    protected Quantity() {
        this.quantity = 0;
    }

    public Quantity(int quantity) {
        validatePositive(quantity);
        this.quantity = quantity;
    }

    private void validatePositive(int quantity) {
        if (quantity <= 0) {
            throw new ShoppingException("수량은 0보다 작거나 같을 수 없습니다.");
        }
    }

    public Quantity increase() {
        return new Quantity(quantity + 1);
    }

    public Quantity update(int quantity) {
        return new Quantity(quantity);
    }

    public int getValue() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
