package shopping.dto.request;

public class CartItemAddRequest {

    private Long productId;

    protected CartItemAddRequest() {
    }

    public CartItemAddRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
