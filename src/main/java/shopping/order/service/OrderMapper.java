package shopping.order.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import shopping.cart.domain.Cart;
import shopping.cart.domain.CartItem;
import shopping.common.domain.Rate;
import shopping.order.domain.Order;
import shopping.order.domain.OrderItem;

@Component
public class OrderMapper {

    public Order mapToOrder(Long memberId, Cart cart, Rate exchangeRate) {
        List<OrderItem> orderItems = cart.getCartItems().stream()
            .map(this::mapToOrderItem)
            .collect(Collectors.toUnmodifiableList());

        return new Order(orderItems, memberId, exchangeRate);
    }

    public OrderItem mapToOrderItem(CartItem cartItem) {
        return new OrderItem(cartItem.getProductId(),
                             cartItem.getProductName(),
                             cartItem.getProductPrice(),
                             cartItem.getQuantity(),
                             cartItem.getImage());
    }
}
