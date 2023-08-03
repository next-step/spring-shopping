package shopping.domain.cart;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cart {

    private final Map<Long, CartProduct> values;

    public Cart(final List<CartProduct> cartProducts) {
        this.values = cartProducts.stream()
            .collect(Collectors.toMap(CartProduct::getProductId, Function.identity()));
    }

    public CartProduct find(final Long productId) {
        return values.get(productId);
    }
}
