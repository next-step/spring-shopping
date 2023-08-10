package shopping.dto;

import shopping.domain.cart.CartProduct;

public class CartProductCreateResponse {

    private final Long cartProductId;

    public CartProductCreateResponse(final Long cartProductId) {
        this.cartProductId = cartProductId;
    }

    public static CartProductCreateResponse from(final CartProduct cartProduct) {
        return new CartProductCreateResponse(cartProduct.getId());
    }

    public Long getCartProductId() {
        return this.cartProductId;
    }
}
