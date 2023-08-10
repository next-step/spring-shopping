package shopping.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.order.Order;

public class OrderDetailResponse {

    private final Long orderId;
    private final List<OrderProductResponse> orderProducts;
    private final long totalPrice;

    @JsonCreator
    public OrderDetailResponse(
        final Long orderId,
        final List<OrderProductResponse> orderProducts,
        final long totalPrice
    ) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
    }

    public static OrderDetailResponse from(final Order order) {
        return new OrderDetailResponse(
            order.getId(),
            order.getOrderProducts().stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList()),
            order.computeTotalPrice()
        );
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public List<OrderProductResponse> getOrderProducts() {
        return this.orderProducts;
    }

    public long getTotalPrice() {
        return this.totalPrice;
    }
}
