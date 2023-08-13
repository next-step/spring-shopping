package shopping.domain.cart;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import shopping.exception.general.InvalidRequestException;

@Embeddable
public class Money {

    private static final int MIN_PRICE = 0;

    private Double price;

    @Enumerated(EnumType.STRING)
    private CurrencyType type;

    protected Money() {
    }

    public Money(Double price, CurrencyType type) {
        validate(price);
        this.price = price;
        this.type = type;
    }

    public Money(Double price) {
        this(price, CurrencyType.KRW);
    }

    private void validate(Double price) {
        if (price == null) {
            throw new InvalidRequestException("가격은 null일 수 없습니다.");
        }
        if (price < MIN_PRICE) {
            throw new InvalidRequestException("가격은 0원 미만일 수 없습니다.");
        }
    }

    public Money exchange(ExchangeRate exchangeRate) {
        exchangeRate.validateExchangeable(type);
        return new Money(price / exchangeRate.getRate(), exchangeRate.getSource());
    }

    public Money multiply(Quantity quantity) {
        return new Money(price * quantity.getQuantity(), type);
    }

    public static Money sum(Money price, Money other) {
        return new Money(price.price + other.price);
    }

    public Double getPrice() {
        return price;
    }

    public CurrencyType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return Objects.equals(price, money1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
