package shopping.domain.cart;

import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CartItemQuantity {

    private static final int MIN_QUANTITY = 1;

    private static final int MAX_QUANTITY = 1000;

    @Column(name = "cart_item_quantity", nullable = false)
    private int value;

    protected CartItemQuantity() {
    }

    private CartItemQuantity(final int value) {
        validate(value);
        this.value = value;
    }

    public static CartItemQuantity from(final int value) {
        return new CartItemQuantity(value);
    }

    public static CartItemQuantity defaultValue() {
        return new CartItemQuantity(MIN_QUANTITY);
    }

    private void validate(final int value) {
        if (value < MIN_QUANTITY || value > MAX_QUANTITY) {
            throw new ShoppingException(ErrorCode.CART_ITEM_QUANTITY_INVALID);
        }
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CartItemQuantity cartItemQuantity = (CartItemQuantity) o;
        return value == cartItemQuantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
