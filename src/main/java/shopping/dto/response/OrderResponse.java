package shopping.dto.response;

import java.util.List;

public class OrderResponse {
    private Long orderId;

    private int orderPrice;

    private double exchangeRate;

    private double exchangedPrice;
    private List<OrderItemResponse> orderItems;

    private OrderResponse() {
    }

    private OrderResponse(
            final Long orderId,
            final int orderPrice,
            final double exchangeRate,
            final List<OrderItemResponse> orderItems
    ) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.exchangeRate = calculateDecimalPoint(exchangeRate, 3);
        this.orderItems = orderItems;
        this.exchangedPrice = calculateDecimalPoint(orderPrice / exchangeRate, 3);
    }

    public static OrderResponse of(
            final Long orderId,
            final int orderPrice,
            final double exchangeRate,
            final List<OrderItemResponse> orderItems
    ) {
        return new OrderResponse(orderId, orderPrice, exchangeRate, orderItems);
    }

    private double calculateDecimalPoint(final double exchangeRate, final int digits) {
        final double decimalPoint = Math.pow(10, digits);
        return Math.round(exchangeRate * decimalPoint) / decimalPoint;
    }

    public double getExchangedPrice() {
        return exchangedPrice;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
