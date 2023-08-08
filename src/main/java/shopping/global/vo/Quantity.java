package shopping.global.vo;

import java.util.Objects;
import shopping.global.exception.ShoppingException;

public class Quantity {

    private final int value;

    protected Quantity() {
        this.value = 0;
    }

    public Quantity(final int value) {
        validateCountLessThanEqualZero(value);

        this.value = value;
    }

    private void validateCountLessThanEqualZero(final int value) {
        if (value <= 0) {
            throw new ShoppingException("장바구니 상품 개수는 0이하면 안됩니다. 입력값: " + value);
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
        final Quantity that = (Quantity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
