package shopping.accept;

public final class OrderRequest {

    private long cartId;

    public OrderRequest() {
    }

    public OrderRequest(long cartId) {
        this.cartId = cartId;
    }

    public long getCartId() {
        return cartId;
    }
}
