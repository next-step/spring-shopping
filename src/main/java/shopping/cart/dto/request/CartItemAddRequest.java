package shopping.cart.dto.request;

public class CartItemAddRequest {

    private Long productId;

    public CartItemAddRequest() {
    }

    public CartItemAddRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
