package shopping.dto.response;

import shopping.domain.cart.CartItem;

public class CartItemResponse {

    private final Long cartItemId;
    private final int quantity;
    private final ProductResponse product;

    private CartItemResponse(
            final Long cartItemId,
            final int quantity,
            final ProductResponse product
    ) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.product = product;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getQuantity(),
                ProductResponse.from(cartItem.getProduct())
        );
    }

    public Long getCartItemId() {
        return this.cartItemId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public ProductResponse getProduct() {
        return this.product;
    }
}
