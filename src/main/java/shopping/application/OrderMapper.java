package shopping.application;

import org.springframework.stereotype.Component;
import shopping.domain.cart.CartItems;
import shopping.domain.cart.Quantity;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order mapFrom(Long userId, CartItems cart) {
        List<OrderItem> orderItems = cart.getItems()
                .stream()
                .map(item -> new OrderItem(item.getProduct(), new Quantity(item.getQuantity())))
                .collect(Collectors.toList());

        long sum = cart.calculateTotalPrice();

        return new Order(userId, orderItems, sum);
    }
}
