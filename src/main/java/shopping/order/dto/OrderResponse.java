package shopping.order.dto;

import java.util.List;
import java.util.stream.Collectors;
import shopping.order.domain.Order;

public class OrderResponse {

    private Long id;
    private List<OrderProductResponse> items;
    private long totalPrice;
    private Double exchangeRate;

    public OrderResponse(
        final Long id,
        final List<OrderProductResponse> items,
        final long totalPrice,
        final Double exchangeRate
    ) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.exchangeRate = exchangeRate;
    }

    public static OrderResponse from(final Order order) {
        List<OrderProductResponse> orderProducts = order.getOrderProducts().stream()
            .map(OrderProductResponse::from)
            .collect(Collectors.toList());

        return new OrderResponse(
            order.getId(),
            orderProducts,
            order.getTotalPrice(),
            order.getExchangeRate()
        );
    }

    public Long getId() {
        return id;
    }

    public List<OrderProductResponse> getItems() {
        return items;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }
}
