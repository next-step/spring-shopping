package shopping.dto.response;

import java.util.List;

public class OrderResponse {
    private Long orderId;

    private int orderPrice;

    private List<OrderItemResponse> orderItems;

    private OrderResponse() {
    }

    private OrderResponse(
            final Long orderId,
            final int orderPrice,
            final List<OrderItemResponse> orderItems
    ) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderItems = orderItems;
    }

    public static OrderResponse of(
            final Long orderId,
            final int orderPrice,
            final List<OrderItemResponse> orderItems
    ) {
        return new OrderResponse(orderId, orderPrice, orderItems);
    }


    public Long getOrderId() {
        return orderId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
