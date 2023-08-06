package shopping.cart.dto.response;

import java.util.List;

public class AllCartItemsResponse {

    private boolean changed;
    private List<CartItemResponse> cartItemResponses;
    private List<CartItemResponse> changedCartItemResponses;

    public AllCartItemsResponse(boolean changed, List<CartItemResponse> cartItemResponses,
        List<CartItemResponse> changedCartItemResponses) {
        this.changed = changed;
        this.cartItemResponses = cartItemResponses;
        this.changedCartItemResponses = changedCartItemResponses;
    }

    public boolean isChanged() {
        return changed;
    }

    public List<CartItemResponse> getCartItemResponses() {
        return cartItemResponses;
    }

    public List<CartItemResponse> getChangedCartItemResponses() {
        return changedCartItemResponses;
    }
}
