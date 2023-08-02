package shopping.domain;

import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Price {

    private final int value;

    protected Price() {
        this.value = 0;
    }

    public Price(int value) {
        validateIsPositive(value);
        
        this.value = value;
    }

    private void validateIsPositive(int value) {
        if (value <= 0) {
            throw new ShoppingException("가격은 음수일 수 없습니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
