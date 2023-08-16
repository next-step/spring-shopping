package shopping.order.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.global.exception.ShoppingException;

@Embeddable
public class Money {

    private long price;

    protected Money() {
    }

    public Money(final long price) {
        validatePriceLessThanEqualZero(price);
        this.price = price;
    }

    private void validatePriceLessThanEqualZero(final long value) {
        if (value < 0) {
            throw new ShoppingException("주문 가격은 0이하면 안됩니다. 입력값: " + value);
        }
    }

    public long getPrice() {
        return this.price;
    }

    public Money addMoney(final long price) {
        return new Money(this.price + price);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Money that = (Money) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }


}
