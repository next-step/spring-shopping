package shopping.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.exception.ArgumentValidateFailException;

@Embeddable
public class Price {

    private static final long MIN_PRICE = 0L;
    private Long price;

    protected Price() {
    }

    public Price(Long price) {
        validate(price);
        this.price = price;
    }

    private void validate(Long price) {
        if (price == null) {
            throw new ArgumentValidateFailException("가격은 null일 수 없습니다.");
        }
        if (price <= MIN_PRICE) {
            throw new ArgumentValidateFailException("가격은 0원 이하일 수 없습니다.");
        }
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
