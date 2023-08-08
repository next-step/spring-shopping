package shopping.dto.response;

import java.util.List;

public class OrderResponse {

    private long id;

    private List<OrderItemResponse> orderItems;

    private OrderResponse() {
    }

    public OrderResponse(long id, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.orderItems = orderItems;
    }

    public long getId() {
        return id;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
