package shopping.cart.dto.request;

public final class CartItemUpdateRequest {

    private int quantity;

    private CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(final int quantity) {
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }
}
