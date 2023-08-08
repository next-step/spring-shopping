package shopping.order.dto;

import java.util.List;
import shopping.order.domain.Order;
import shopping.order.domain.OrderProduct;

public class OrderResponse {

    private Long id;
    private List<OrderProduct> orderProducts;
    private int totalPrice;

    public OrderResponse(
        final Long id,
        final List<OrderProduct> orderProducts,
        final int totalPrice
    ) {
        this.id = id;
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
    }

    public static OrderResponse from(final Order order) {
        return new OrderResponse(order.getId(), order.getOrderProducts(), order.getTotalPrice());
    }

    public Long getId() {
        return id;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
