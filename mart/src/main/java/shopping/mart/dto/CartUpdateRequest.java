package shopping.mart.dto;

public final class CartUpdateRequest {

    private long productId;
    private int count;

    CartUpdateRequest() {
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
