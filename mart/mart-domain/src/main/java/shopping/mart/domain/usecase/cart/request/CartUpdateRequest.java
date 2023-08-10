package shopping.mart.domain.usecase.cart.request;

public final class CartUpdateRequest {

    private long productId;
    private int count;

    public CartUpdateRequest() {
    }

    public CartUpdateRequest(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
