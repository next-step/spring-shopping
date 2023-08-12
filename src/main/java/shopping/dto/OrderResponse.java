package shopping.dto;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.Order;

public class OrderResponse {

    private final long orderId;
    private final List<OrderProductResponse> orderProducts;
    private final double exchangeRate;

    private OrderResponse(long orderId, List<OrderProductResponse> orderProducts,
        double exchangeRate) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.exchangeRate = exchangeRate;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getOrderProducts()
                .stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList()),
            order.getExchangeRate());
    }

    public long getOrderId() {
        return orderId;
    }

    public List<OrderProductResponse> getOrderProducts() {
        return orderProducts;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}
