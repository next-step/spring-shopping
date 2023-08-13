package shopping.dto.response;


public class OrderCreateResponse {
    private Long orderId;

    private OrderCreateResponse(final Long orderId) {
        this.orderId = orderId;
    }

    private OrderCreateResponse() {
    }

    public static OrderCreateResponse from(final Long orderId) {
        return new OrderCreateResponse(orderId);
    }

    public Long getOrderId() {
        return orderId;
    }
}
