package shopping.dto.response;

import java.util.List;

public class OrderResponse {
    private static final int DECIMAL_PLACE = 3;
    private static final int BASE = 10;
    private Long orderId;

    private long orderPrice;

    private double exchangeRate;

    private double exchangedPrice;
    private List<OrderItemResponse> orderItems;

    private OrderResponse() {
    }

    private OrderResponse(
            final Long orderId,
            final long orderPrice,
            final double exchangeRate,
            final List<OrderItemResponse> orderItems
    ) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.exchangeRate = calculateDecimalThreePoint(exchangeRate);
        this.orderItems = orderItems;
        this.exchangedPrice = calculateDecimalThreePoint(orderPrice / exchangeRate);
    }

    public static OrderResponse of(
            final Long orderId,
            final long orderPrice,
            final double exchangeRate,
            final List<OrderItemResponse> orderItems
    ) {
        return new OrderResponse(orderId, orderPrice, exchangeRate, orderItems);
    }

    private double calculateDecimalThreePoint(final double exchangeRate) {
        final double decimalPoint = Math.pow(BASE, DECIMAL_PLACE);
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

    public long getOrderPrice() {
        return orderPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
