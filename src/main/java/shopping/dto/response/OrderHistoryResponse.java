package shopping.dto.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import shopping.domain.order.Order;

public class OrderHistoryResponse {

    private final Long orderId;
    private final List<OrderItemResponse> orderItems;

    private OrderHistoryResponse(final Long orderId, final List<OrderItemResponse> orderItems) {
        this.orderId = orderId;
        this.orderItems = orderItems;
    }

    public static OrderHistoryResponse from(final Order order) {
        final List<OrderItemResponse> orderItems = order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .collect(toList());

        return new OrderHistoryResponse(order.getId(), orderItems);
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public List<OrderItemResponse> getOrderItems() {
        return this.orderItems;
    }
}
