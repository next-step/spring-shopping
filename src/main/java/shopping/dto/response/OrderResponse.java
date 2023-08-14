package shopping.dto.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import shopping.domain.order.Order;

public class OrderResponse {

    private final Long orderId;
    private final List<OrderItemResponse> orderItems;
    private final long totalPrice;
    private final double exchangeRate;
    private final double convertedTotalPrice;

    private OrderResponse(
            final Long orderId,
            final List<OrderItemResponse> orderItems,
            final long totalPrice,
            final double exchangeRate,
            final double convertedTotalPrice
    ) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.exchangeRate = exchangeRate;
        this.convertedTotalPrice = convertedTotalPrice;
    }

    public static OrderResponse from(final Order order) {
        final List<OrderItemResponse> orderItems = order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .collect(toList());

        return new OrderResponse(
                order.getId(),
                orderItems,
                order.getOrderTotalPrice(),
                order.getExchangeRate(),
                order.getConvertedOrderTotalPrice()
        );
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public List<OrderItemResponse> getOrderItems() {
        return this.orderItems;
    }

    public long getTotalPrice() {
        return this.totalPrice;
    }

    public double getExchangeRate() {
        return this.exchangeRate;
    }

    public double getConvertedTotalPrice() {
        return this.convertedTotalPrice;
    }
}
