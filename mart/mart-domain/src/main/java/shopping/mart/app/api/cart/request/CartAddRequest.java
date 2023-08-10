package shopping.mart.app.api.cart.request;

public final class CartAddRequest {

    private Long productId;

    public CartAddRequest() {
    }

    public CartAddRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
