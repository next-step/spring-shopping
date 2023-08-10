package shopping.domain.cart;

import java.util.List;
import shopping.exception.CartExceptionType;
import shopping.exception.ShoppingException;

public class Cart {

    private final Long memberId;
    private final List<CartProduct> cartProducts;

    public Cart(final Long memberId, final List<CartProduct> cartProducts) {
        validateIsNotEmptyCartProducts(cartProducts);

        this.memberId = memberId;
        this.cartProducts = cartProducts;
    }

    private void validateIsNotEmptyCartProducts(final List<CartProduct> cartProducts) {
        if (cartProducts.isEmpty()) {
            throw new ShoppingException(CartExceptionType.EMPTY_CART);
        }
    }


    public Long getMemberId() {
        return this.memberId;
    }

    public List<CartProduct> getCartProducts() {
        return this.cartProducts;
    }
}
