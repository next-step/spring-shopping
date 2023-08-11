package shopping.dto.response;

public class OrderIdResponse {

    private Long orderId;

    private OrderIdResponse() {
    }

    public OrderIdResponse(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
