package shopping.cart.dto.response;

import java.util.List;

public class AllCartItemsResponse {

    private boolean isChanged;
    private List<CartItemResponse> cartItemResponses;
    private List<CartItemResponse> changedCartItemResponses;

    private AllCartItemsResponse() {
    }

    public AllCartItemsResponse(boolean isChanged, List<CartItemResponse> cartItemResponses,
        List<CartItemResponse> changedCartItemResponses) {
        this.isChanged = isChanged;
        this.cartItemResponses = cartItemResponses;
        this.changedCartItemResponses = changedCartItemResponses;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public List<CartItemResponse> getCartItemResponses() {
        return cartItemResponses;
    }

    public List<CartItemResponse> getChangedCartItemResponses() {
        return changedCartItemResponses;
    }
}
