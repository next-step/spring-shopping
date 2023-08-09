package shopping.dto.response;

import java.util.List;

public class OrderResponse {

    private Long id;

    private long totalPrice;

    private List<OrderItemResponse> orderItems;

    private OrderResponse() {
    }

    public OrderResponse(Long id, long totalPrice, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
