package shopping.mart.dto;

public final class CartAddRequest {

    private Long productId;

    CartAddRequest() {
    }

    public CartAddRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
