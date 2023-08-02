package shopping.cart.dto.request;

public class CartItemCreationRequest {
    private Long productId;

    private CartItemCreationRequest() {
    }

    public Long getProductId() {
        return productId;
    }
}
