package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartProductRequest {

    private final Long productId;

    @JsonCreator
    public CartProductRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return this.productId;
    }
}
