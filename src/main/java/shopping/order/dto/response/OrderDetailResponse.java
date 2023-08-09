package shopping.order.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.order.domain.Order;

public class OrderDetailResponse {

    private Long orderId;
    private List<OrderItemDetailResponse> orderItems;
    private String totalPrice;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(Order order) {
        this.orderId = order.getId();
        this.orderItems = order.getOrderItems().getOrderItems().stream()
            .map(OrderItemDetailResponse::new)
            .collect(Collectors.toList());
        this.totalPrice = order.getTotalPrice().getAmount().toPlainString();
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderItemDetailResponse> getOrderItems() {
        return orderItems;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

}
