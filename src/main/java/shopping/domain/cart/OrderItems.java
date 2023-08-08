package shopping.domain.cart;

import shopping.exception.NoCartItemForOrderException;
import shopping.exception.NoOrderItemException;
import shopping.exception.NotSameOrderException;
import shopping.exception.UserNotMatchException;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItems {

    private final Long orderId;
    private final Long userId;
    private final List<OrderItem> items;

    private OrderItems(Long orderId, Long userId, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
    }

    public static OrderItems from(List<CartItem> cartItems, Order order) {
        validateCartItemExists(cartItems);
        return OrderItems.of(cartItems.stream()
                .map(cartItem -> OrderItem.from(cartItem, order))
                .collect(Collectors.toList()));
    }

    public static OrderItems of(List<OrderItem> orderItems) {
        validateNotEmpty(orderItems);
        Order order = reduceOrder(orderItems);
        return new OrderItems(order.getId(), order.getUserId(), orderItems);
    }

    private static void validateCartItemExists(List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new NoCartItemForOrderException();
        }
    }

    private static void validateNotEmpty(List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new NoOrderItemException();
        }
    }

    private static Order reduceOrder(List<OrderItem> orderItems) {
        Order order = orderItems.get(0).getOrder();
        validateSameOrder(orderItems, order);
        return order;
    }

    private static void validateSameOrder(List<OrderItem> orderItems, Order order) {
        if (!orderItems.stream().allMatch(item -> item.getOrder().equals(order))) {
            throw new NotSameOrderException();
        }
    }

    public void validateUserOwns(Long userId) {
        if (!this.userId.equals(userId)) {
            throw new UserNotMatchException();
        }
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
