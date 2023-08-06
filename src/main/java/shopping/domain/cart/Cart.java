package shopping.domain.cart;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cart {

    private final Map<Long, CartProduct> cartProduct;

    public Cart(final List<CartProduct> cartProducts) {
        this.cartProduct = cartProducts.stream()
            .collect(Collectors.toMap(CartProduct::getProductId, Function.identity()));
    }

    public CartProduct findByProductId(final Long productId) {
        return cartProduct.get(productId);
    }
}
