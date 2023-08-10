package shopping.service;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import shopping.domain.cart.Cart;
import shopping.domain.order.Order;
import shopping.domain.order.OrderProduct;

@Component
public class OrderMapper {

    public Order mapFrom(final Cart cart) {
        return new Order(
            cart.getMemberId(),
            cart.getCartProducts().stream()
                .map(cartProduct -> new OrderProduct(
                    cartProduct.getProductImage(),
                    cartProduct.getProductName(),
                    cartProduct.getProductPrice(),
                    cartProduct.getQuantity()
                )).collect(Collectors.toList())
        );
    }
}
