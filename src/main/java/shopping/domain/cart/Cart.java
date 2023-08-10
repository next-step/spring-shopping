package shopping.domain.cart;

import java.util.List;

public class Cart {

    private final Long memberId;
    private final List<CartProduct> cartProducts;

    public Cart(final Long memberId, final List<CartProduct> cartProducts) {
        this.memberId = memberId;
        this.cartProducts = cartProducts;
    }

    public List<CartProduct> getCartProducts() {
        return this.cartProducts;
    }
}
