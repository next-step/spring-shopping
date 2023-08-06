package shopping.cart.domain.vo;

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

    public int getValue() {
        return value;
    }

    public Quantity increase() {
        return new Quantity(this.value + 1);
    }
}
