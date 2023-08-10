package shopping.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.order.Order;
import shopping.domain.order.OrderProduct;

public class OrderDetailResponse {

    private final Long orderId;
    private final List<OrderProductResponse> orderProducts;
    private final long totalPrice;

    public OrderDetailResponse(
        final Long orderId,
        final List<OrderProduct> orderProducts
    ) {
        this(
            orderId,
            orderProducts.stream()
                .map(OrderProductResponse::from)
                .collect(Collectors.toList()),
            orderProducts.stream()
                .mapToLong(OrderProduct::getOrderedPrice)
                .sum()
        );
    }

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
            order.getOrderProducts()
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
