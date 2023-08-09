package shopping.order.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.order.domain.Order;

public class OrdersResponse {

    private List<OrderResponse> orders;

    private OrdersResponse() {
    }

    public OrdersResponse(List<Order> orders) {
        this.orders = orders.stream()
            .map(OrderResponse::new)
            .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrders() {
        return orders;
    }

    public static class OrderResponse {

        private Long orderId;
        private List<OrderItemDetailResponse> orderItems;

        private OrderResponse() {
        }

        public OrderResponse(Order order) {
            this.orderId = order.getId();
            this.orderItems = order.getOrderItems().getOrderItems().stream()
                .map(OrderItemDetailResponse::new)
                .collect(Collectors.toList());
        }

        public Long getOrderId() {
            return orderId;
        }

        public List<OrderItemDetailResponse> getOrderItems() {
            return orderItems;
        }
    }

}
