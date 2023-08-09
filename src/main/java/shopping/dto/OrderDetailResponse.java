package shopping.dto;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.Order;

public class OrderDetailResponse {

    private final long orderId;
    private final List<OrderProductResponse> orderProducts;
    private final long totalPrice;

    private OrderDetailResponse(long orderId, List<OrderProductResponse> orderProducts, long totalPrice) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
    }

    public static OrderDetailResponse from(Order order) {
        return new OrderDetailResponse(
            order.getId(),
            order.getOrderProducts()
                .stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList()),
            order.calculateTotalPrice()
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
}
