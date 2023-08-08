package shopping.mart.app.api.cart.event;

public final class CartOrderedEvent {
    private final long cartId;

    public CartOrderedEvent(long cartId) {
        this.cartId = cartId;
    }

    public long getCartId() {
        return cartId;
    }
}
