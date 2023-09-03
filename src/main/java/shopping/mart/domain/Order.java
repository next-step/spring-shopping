package shopping.mart.domain;

import shopping.core.exception.BadRequestException;
import shopping.mart.domain.status.OrderExceptionStatus;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

public class Order {

    private Long orderId;
    private final Map<Product, Integer> productCounts;
    private final Price totalPrice;
    private final Double currencyByUsd;

    public Order(final Map<Product, Integer> productCounts, final Double currency) {
        this(null, productCounts, currency);
    }

    public Order(final Long orderId, final Map<Product, Integer> productCounts, final Double currency) {
        validate(productCounts);
        this.orderId = orderId;
        this.productCounts = productCounts;
        this.totalPrice = calcTotalPrice(productCounts);
        this.currencyByUsd = currency;
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

    public Long getOrderId() {
        return orderId;
    }

    public Map<Product, Integer> getProductCounts() {
        return productCounts;
    }

    public String getTotalPrice() {
        return totalPrice.getValue().toString();
    }

    public Double getCurrencyByUsd() {
        return currencyByUsd;
    }

    public String getUsd() {
        if (Objects.isNull(currencyByUsd)) {
            return null;
        }

        BigInteger usd = new BigInteger(currencyByUsd.toString());

        return totalPrice.getValue().divide(usd).toString();
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
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
