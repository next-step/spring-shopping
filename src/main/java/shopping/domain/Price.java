package shopping.domain;

import javax.persistence.Embeddable;

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
            throw new IllegalArgumentException("가격은 null일 수 없습니다.");
        }
        if (price <= MIN_PRICE) {
            throw new IllegalArgumentException("가격은 0원 이하일 수 없습니다.");
        }
    }

    public Long getPrice() {
        return price;
    }
}
