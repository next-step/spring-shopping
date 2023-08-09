package shopping.domain.cart;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cart {

    private final Map<Long, CartProduct> productIdByCarProduct;

    public Cart(final List<CartProduct> cartProducts) {
        this.productIdByCarProduct = cartProducts.stream()
            .collect(Collectors.toMap(CartProduct::getProductId, Function.identity()));
    }

    public CartProduct find(final Long productId) {
        return productIdByCarProduct.get(productId);
    }
}
