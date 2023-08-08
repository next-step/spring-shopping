package shopping.mart.app.api.cart.event;

public final class CartClearEvent {
    private final long cartId;

    public CartClearEvent(long cartId) {
        this.cartId = cartId;
    }

    public long getCartId() {
        return cartId;
    }
}
