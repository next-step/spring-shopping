package shopping.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.order.Order;

public class OrderHistoryResponse {

    private final Long orderId;
    private final List<OrderProductResponse> orderProducts;

    public OrderHistoryResponse(
        final Long orderId,
        final List<OrderProductResponse> orderProducts
    ) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
    }

    public static OrderHistoryResponse from(final Order order) {
        return new OrderHistoryResponse(
            order.getId(),
            order.getOrderProducts().stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList())
        );
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public List<OrderProductResponse> getOrderProducts() {
        return this.orderProducts;
    }
}
