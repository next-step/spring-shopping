package shopping.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.order.Order;
import shopping.exchange.CurrencyExchangeRate;

public class OrderDetailResponse {

    private final Long orderId;
    private final List<OrderProductResponse> orderProducts;
    private final long totalPrice;
    private final Double currencyRate;

    @JsonCreator
    public OrderDetailResponse(
        final Long orderId,
        final List<OrderProductResponse> orderProducts,
        final long totalPrice,
        final Double currencyRate
    ) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
        this.currencyRate = currencyRate;
    }

    public static OrderDetailResponse from(final Order order) {
        return new OrderDetailResponse(
            order.getId(),
            generateOrderProductResponses(order),
            order.computeTotalPrice(),
            null
        );
    }

    public static OrderDetailResponse of(
        final Order order,
        final CurrencyExchangeRate currencyExchangeRate
    ) {
        return new OrderDetailResponse(
            order.getId(),
            generateOrderProductResponses(order),
            order.computeTotalPrice(),
            currencyExchangeRate.getRate()
        );
    }

    private static List<OrderProductResponse> generateOrderProductResponses(final Order order) {
        return order.getOrderProducts().stream()
            .map(OrderProductResponse::from)
            .collect(Collectors.toList());
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public List<OrderProductResponse> getOrderProducts() {
        return this.orderProducts;
    }

    public long getTotalPrice() {
        return this.totalPrice;
    }

    public Double getCurrencyRate() {
        return this.currencyRate;
    }
}
