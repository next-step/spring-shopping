package shopping.cart.domain.vo;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.http.HttpStatus;
import shopping.exception.WooWaException;

@Embeddable
public class Quantity {

    @Column(name = "quantity")
    private int value;

    protected Quantity() {
    }

    public Quantity(int quantity) {
        validateNoneNegative(quantity);
        this.value = quantity;
    }

    private static void validateNoneNegative(int quantity) {
        if (quantity < 0) {
            throw new WooWaException("수량은 음수일 수 없습니다 quantity: '" + quantity + "'", HttpStatus.BAD_REQUEST);
        }
    }

    public Quantity increase() {
        return new Quantity(this.value + 1);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
