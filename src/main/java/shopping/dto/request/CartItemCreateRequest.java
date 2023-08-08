package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemCreateRequest {

    private final Long productId;

    @JsonCreator
    public CartItemCreateRequest(@JsonProperty("productId") Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
