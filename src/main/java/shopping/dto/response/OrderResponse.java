package shopping.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.entity.OrderEntity;

public class OrderResponse {

    private final Long id;
    private final int totalPrice;
    private final List<OrderItemResponse> orderItems;

    public OrderResponse(Long id, int totalPrice, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public static OrderResponse from(OrderEntity order) {
        return new OrderResponse(
            order.getId(),
            order.getTotalPrice(),
            order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    public Long getId() {
        return id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

}
