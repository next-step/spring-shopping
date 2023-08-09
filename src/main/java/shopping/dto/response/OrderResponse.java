package shopping.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.OrderItem;

public class OrderResponse {

    private Long id;

    private List<OrderItemResponse> orderItems;

    private OrderResponse() {
    }

    public OrderResponse(Long id, List<OrderItem> orderItems) {
        this.id = id;
        this.orderItems = orderItems.stream()
            .map(OrderItemResponse::of)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
