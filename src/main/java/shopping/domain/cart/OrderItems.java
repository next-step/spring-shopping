package shopping.domain.cart;

import shopping.exception.auth.UserNotMatchException;
import shopping.exception.cart.NoCartItemForOrderException;
import shopping.exception.cart.NoOrderItemException;
import shopping.exception.cart.NotSameOrderException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderItems {

    private final Long orderId;
    private final Long userId;
    private final ExchangeRate exchangeRate;
    private final List<OrderItem> items;

    private OrderItems(Long orderId, Long userId, ExchangeRate exchangeRate, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.exchangeRate = exchangeRate;
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
        return new OrderItems(order.getId(), order.getUserId(), order.getExchangeRate(), orderItems);
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

    public Money totalPrice() {
        return items.stream()
                .map(OrderItem::totalPrice)
                .reduce(new Money(0.0), Money::sum);
    }

    public Optional<Money> exchangePrice() {
        if (exchangeRate == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(totalPrice().exchange(exchangeRate));
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public Optional<ExchangeRate> getExchangeRate() {
        return Optional.ofNullable(exchangeRate);
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
