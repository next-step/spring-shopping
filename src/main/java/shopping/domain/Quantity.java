package shopping.domain;

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
            throw new IllegalArgumentException("수량은 Null 일수 없습니다.");
        }
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("수량은 " + MIN_QUANTITY + "이상이어야 합니다.");
        }
    }

    public Integer getQuantity() {
        return quantity;
    }
}
