package shopping.mart.domain;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Objects;
import shopping.core.exception.StatusCodeException;
import shopping.mart.domain.status.ProductExceptionStatus;

final class Price {

    private final BigInteger value;

    Price(final String price) {
        validPrice(price);
        this.value = new BigInteger(price);
    }

    private void validPrice(final String price) {
        validNumber(price);
        validNegativePrice(price);
    }

    private void validNumber(final String price) {
        if (price.matches("-?\\d+")) {
            return;
        }
        throw new StatusCodeException(MessageFormat.format("price \"{0}\"는 정수만 입력할 수 있습니다.", value),
                ProductExceptionStatus.NOT_NUMBER.getStatus());
    }

    private void validNegativePrice(final String price) {
        if (Long.parseLong(price) < 0) {
            throw new StatusCodeException(MessageFormat.format("price \"{0}\"는 0보다 작을 수 없습니다.", value),
                    ProductExceptionStatus.NEGATIVE_PRICE.getStatus());
        }
    }

    BigInteger getValue() {
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

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                '}';
    }
}
