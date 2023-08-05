package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateNumberArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemCreateRequest {

    private final Long productId;

    @JsonCreator
    public CartItemCreateRequest(@JsonProperty("productId") Long productId) {
        validate(productId);
        this.productId = productId;
    }

    private void validate(Long productId) {
        validateNumberArgument(productId, "상품 아이디");
    }

    public Long getProductId() {
        return productId;
    }
}
