package shopping.mart.app.api.cart.event;

public final class CartClearEvent {
    private final long userId;

    public CartClearEvent(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
