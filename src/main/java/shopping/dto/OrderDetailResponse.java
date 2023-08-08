package shopping.dto;

import shopping.domain.order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailResponse {

    private Long id;
    private List<OrderItemResponse> orderItems;
    private long totalPrice;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(Long id, List<OrderItemResponse> orderItems, long totalPrice) {
        this.id = id;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public static OrderDetailResponse from(Order order) {
        return new OrderDetailResponse(
                order.getId(),
                order.getOrderItems()
                        .stream()
                        .map(OrderItemResponse::from)
                        .collect(Collectors.toList()),
                order.getTotalPrice()
        );
    }
}
