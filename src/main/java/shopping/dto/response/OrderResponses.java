package shopping.dto.response;

import java.util.List;

public class OrderResponses {
    private List<OrderResponse> orders;

    private OrderResponses() {
    }

    private OrderResponses(final List<OrderResponse> orders) {
        this.orders = orders;
    }

    public static OrderResponses from(final List<OrderResponse> orders) {
        return new OrderResponses(orders);
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }
}
