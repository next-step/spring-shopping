package shopping.mart.domain;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Objects;
import shopping.mart.domain.exception.InvalidPriceException;

public final class Price {

    private static final BigInteger NEGATIVE_NUMBER = BigInteger.valueOf(-1);

    private final BigInteger value;

    public Price(final String price) {
        BigInteger notValidatedPrice = getValidNumber(price);
        validPrice(notValidatedPrice);
        this.value = notValidatedPrice;
    }

    private void validPrice(final BigInteger price) {
        validNegativePrice(price);
    }

    private BigInteger getValidNumber(final String price) {
        try {
            return new BigInteger(price);
        } catch (NumberFormatException numberFormatException) {
            throw new InvalidPriceException(
                MessageFormat.format("price \"{0}\"는 정수만 입력할 수 있습니다.", price));
        }
    }

    private void validNegativePrice(final BigInteger price) {
        if (price.compareTo(NEGATIVE_NUMBER) < 0) {
            throw new InvalidPriceException(
                MessageFormat.format("price \"{0}\"는 0보다 작을 수 없습니다.", price));
        }
    }

    public BigInteger getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
