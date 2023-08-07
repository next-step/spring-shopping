package shopping.domain.cart;

import shopping.exception.InvalidRequestException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Price {

    private static final int MIN_PRICE = 0;
    private Long price;

    protected Price() {
    }

    public Price(Long price) {
        validate(price);
        this.price = price;
    }

    private void validate(Long price) {
        if (price == null) {
            throw new InvalidRequestException("가격은 null일 수 없습니다.");
        }
        if (price <= MIN_PRICE) {
            throw new InvalidRequestException("가격은 0원 이하일 수 없습니다.");
        }
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
