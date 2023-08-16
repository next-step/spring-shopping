package shopping.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartProductCreateRequest {

    private final Long productId;

    @JsonCreator
    public CartProductCreateRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return this.productId;
    }
}
