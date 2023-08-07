package shopping.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

public class CartCreateRequest {
    private Long productId;

    private CartCreateRequest() {
    }

    @JsonCreator
    public CartCreateRequest(final Long productId) {
        validateNotNull(productId);
        validatePositive(productId);

        this.productId = productId;
    }

    private void validatePositive(final Long productId) {
        if (productId <= 0) {
            throw new ShoppingException(ErrorType.PRODUCT_INVALID);
        }
    }

    private void validateNotNull(final Long productId) {
        if (productId == null) {
            throw new ShoppingException(ErrorType.PRODUCT_NULL);
        }
    }

    public Long getProductId() {
        return productId;
    }
}
