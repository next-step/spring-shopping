package shopping.dto.response;

import java.util.List;

public class CartItemResponses {

    private List<CartItemResponse> cartItems;

    private CartItemResponses() {
    }

    private CartItemResponses(final List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public static CartItemResponses from(final List<CartItemResponse> cartItems) {
        return new CartItemResponses(cartItems);
    }

    public List<CartItemResponse> getCartItems() {
        return this.cartItems;
    }
}
