package shopping.service;

import java.util.List;
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
            getOrderProducts(cart)
        );
    }

    public Order mapOf(final Cart cart, final double rate) {
        return new Order(
            cart.getMemberId(),
            rate,
            getOrderProducts(cart)
        );
    }

    private List<OrderProduct> getOrderProducts(final Cart cart) {
        return cart.getCartProducts().stream()
            .map(cartProduct -> new OrderProduct(
                cartProduct.getProductImage(),
                cartProduct.getProductName(),
                cartProduct.getProductPrice(),
                cartProduct.getQuantity()
            )).collect(Collectors.toList());
    }
}
