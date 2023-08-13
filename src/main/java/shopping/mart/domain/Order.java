package shopping.mart.domain;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import shopping.core.exception.BadRequestException;
import shopping.mart.domain.status.OrderExceptionStatus;

public class Order {

    private final Map<Product, Integer> productCounts;
    private final Price totalPrice;

    public Order(final Map<Product, Integer> productCounts) {
        validate(productCounts);
        this.productCounts = productCounts;
        this.totalPrice = calcTotalPrice(productCounts);
    }

    private void validate(final Map<Product, Integer> productCounts) {
        if (productCounts.isEmpty()) {
            throw new BadRequestException("장바구니에 물건이 존재하지 않습니다.", OrderExceptionStatus.EMPTY_CART.getStatus());
        }
    }

    private Price calcTotalPrice(final Map<Product, Integer> productCounts) {
        String calculated = productCounts.entrySet().stream()
                .map(item -> new BigInteger(item.getKey().getPrice()).multiply(new BigInteger(item.getValue().toString())))
                .reduce(BigInteger::add)
                .orElseThrow(() -> new IllegalStateException("장바구니가 비어 있어 총 결제 금액을 계산할 수 없습니다."))
                .toString();

        return new Price(calculated);
    }

    public Map<Product, Integer> getProductCounts() {
        return productCounts;
    }

    public String getTotalPrice() {
        return totalPrice.getValue().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (!Objects.equals(productCounts, order.productCounts)) {
            return false;
        }
        return Objects.equals(totalPrice, order.totalPrice);
    }

    @Override
    public int hashCode() {
        int result = productCounts != null ? productCounts.hashCode() : 0;
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }
}
