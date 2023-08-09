package shopping.domain.cart;

import shopping.exception.general.InvalidRequestException;

import javax.persistence.Embeddable;

@Embeddable
public class Quantity {

    private static final int MIN_QUANTITY = 1;
    private static final int ADD_COUNT = 1;
    private final int quantity;

    protected Quantity() {
        this.quantity = MIN_QUANTITY;
    }

    public Quantity(int quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new InvalidRequestException("수량은 " + MIN_QUANTITY + "이상이어야 합니다.");
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public Quantity addQuantity() {
        return new Quantity(this.quantity + ADD_COUNT);
    }
}
