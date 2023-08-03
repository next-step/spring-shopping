package shopping.dto.request;

public class CartItemAddRequest {

    private Long productId;

    private CartItemAddRequest() {
    }

    public CartItemAddRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return this.productId;
    }
}
