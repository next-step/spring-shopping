package shopping.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.domain.order.Order;

public class OrderCreateResponse {

    private final Long orderId;

    @JsonCreator
    public OrderCreateResponse(final Long orderId) {
        this.orderId = orderId;
    }

    public static OrderCreateResponse from(final Order order) {
        return new OrderCreateResponse(order.getId());
    }

    public Long getOrderId() {
        return this.orderId;
    }
}
