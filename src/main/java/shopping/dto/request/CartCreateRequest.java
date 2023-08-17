package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.exception.ProductIdInvalidException;
import shopping.exception.ProductIdNotFoundException;

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
            throw new ProductIdInvalidException();
        }
    }

    private void validateNotNull(final Long productId) {
        if (productId == null) {
            throw new ProductIdNotFoundException();
        }
    }

    public Long getProductId() {
        return productId;
    }
}
