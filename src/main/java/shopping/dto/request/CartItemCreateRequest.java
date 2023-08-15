package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateNumberNotNullAndPositive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemCreateRequest {

    private static final String PRODUCT_ID_NAME = "productId";

    private final Long productId;

    @JsonCreator
    public CartItemCreateRequest(@JsonProperty(PRODUCT_ID_NAME) final Long productId) {
        validate(productId);
        this.productId = productId;
    }

    private void validate(Long productId) {
        validateNumberNotNullAndPositive(productId, PRODUCT_ID_NAME);
    }

    public Long getProductId() {
        return productId;
    }
}
