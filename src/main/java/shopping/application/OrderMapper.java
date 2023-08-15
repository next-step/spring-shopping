package shopping.application;

import org.springframework.stereotype.Component;
import shopping.domain.cart.Cart;
import shopping.domain.cart.CartItem;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.domain.order.OrderItemPrice;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order mapOrderFrom(final Cart cart, final double exchangeRate) {
        List<OrderItem> orderItems = cart.getItems()
                .stream()
                .map(this::mapOrderItemFrom)
                .collect(Collectors.toList());

        long sum = cart.calculateTotalPrice();

        return new Order(cart.getUserId(), orderItems, sum, exchangeRate);
    }

    private OrderItem mapOrderItemFrom(final CartItem item) {
        return new OrderItem(item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getImage(),
                new OrderItemPrice(item.getProduct().getPrice().getPrice()),
                item.getQuantity());
    }
}
