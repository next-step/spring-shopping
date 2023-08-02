package shopping.dto.response;

import shopping.domain.cart.CartItem;

public class CartItemResponse {

    private final Long cartItemId;
    private final ProductResponse product;

    protected CartItemResponse(final Long cartItemId, final ProductResponse product) {
        this.cartItemId = cartItemId;
        this.product = product;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                ProductResponse.from(cartItem.getProduct())
        );
    }

    public Long getCartItemId() {
        return this.cartItemId;
    }

    public ProductResponse getProduct() {
        return this.product;
    }
}
