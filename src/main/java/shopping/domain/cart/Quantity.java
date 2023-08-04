package shopping.domain.cart;

import shopping.exception.InvalidRequestException;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {

    private static final int MIN_QUANTITY = 1;
    private Integer quantity;

    protected Quantity() {
        this.quantity = MIN_QUANTITY;
    }

    public Quantity(Integer quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        if (quantity == null) {
            throw new InvalidRequestException("수량은 Null 일수 없습니다.");
        }
        if (quantity < MIN_QUANTITY) {
            throw new InvalidRequestException("수량은 " + MIN_QUANTITY + "이상이어야 합니다.");
        }
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity1 = (Quantity) o;
        return Objects.equals(quantity, quantity1.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
