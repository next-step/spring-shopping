package shopping.domain.cart;

import shopping.exception.NoOrderItemException;
import shopping.exception.NotSameOrderException;

import java.util.List;

public class OrderItems {

    private Long id;
    private List<OrderItem> orderItems;

    private OrderItems(Long id, List<OrderItem> orderItems) {
        this.id = id;
        this.orderItems = orderItems;
    }

    public static OrderItems of(List<OrderItem> orderItems) {
        validateNotEmpty(orderItems);
        return new OrderItems(reduceOrderId(orderItems), orderItems);
    }

    private static void validateNotEmpty(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new NoOrderItemException();
        }
    }

    private static Long reduceOrderId(List<OrderItem> orderItems) {
        Long orderId = orderItems.get(0).getOrder().getId();
        validateSameId(orderItems, orderId);
        return orderId;
    }

    private static void validateSameId(List<OrderItem> orderItems, Long orderId) {
        if (!orderItems.stream()
                .allMatch(orderItem -> orderItem.getOrder().getId().equals(orderId))) {
            throw new NotSameOrderException();
        }
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
