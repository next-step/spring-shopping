package shopping.dto.request;

import java.util.List;

public class OrderRequest {

    private List<OrderItemRequest> orderItems;

    private OrderRequest() {
    }

    public OrderRequest(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }
}
