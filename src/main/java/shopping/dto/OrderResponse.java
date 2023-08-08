package shopping.dto;

import shopping.domain.order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    private Long id;
    private List<OrderItemResponse> orderItems;

    private OrderResponse() {
    }

    public OrderResponse(Long id, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderItems()
                        .stream()
                        .map(OrderItemResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
