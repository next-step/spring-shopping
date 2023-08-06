package shopping.cart.dto.request;

public class CartItemCreationRequest {

    private Long productId;

    private CartItemCreationRequest() {
    }

    public CartItemCreationRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
