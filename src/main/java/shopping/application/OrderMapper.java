package shopping.application;

import org.springframework.stereotype.Component;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.CartItems;
import shopping.domain.cart.Quantity;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order mapOrderFrom(Long userId, CartItems cart) {
        List<OrderItem> orderItems = cart.getItems()
                .stream()
                .map(this::mapOrderItemFrom)
                .collect(Collectors.toList());

        long sum = cart.calculateTotalPrice();

        return new Order(userId, orderItems, sum);
    }

    private OrderItem mapOrderItemFrom(CartItem item) {
        return new OrderItem(item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getImage(),
                item.getProduct().getPrice(),
                new Quantity(item.getQuantity()));
    }
}
