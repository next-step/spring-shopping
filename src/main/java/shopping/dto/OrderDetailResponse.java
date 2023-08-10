package shopping.dto;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.Order;

public class OrderDetailResponse {

    private final long orderId;
    private final List<OrderProductResponse> orderProducts;
    private final long totalPrice;
    private final double exchangeRate;

    private OrderDetailResponse(
        long orderId,
        List<OrderProductResponse> orderProducts,
        long totalPrice,
        double exchangeRate
    ) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
        this.exchangeRate = exchangeRate;
    }

    public static OrderDetailResponse from(Order order) {
        return new OrderDetailResponse(
            order.getId(),
            order.getOrderProducts()
                .stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList()),
            order.calculateTotalPrice(),
            order.getExchangeRate()
        );
    }

    public long getOrderId() {
        return orderId;
    }

    public List<OrderProductResponse> getOrderProducts() {
        return orderProducts;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}
