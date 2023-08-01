package shopping.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.exception.ShoppingException;

public class CartCreateRequest {
    private Long productId;

    private CartCreateRequest() {
    }

    @JsonCreator
    public CartCreateRequest(Long productId) {
        validateNotNull(productId);
        validatePositive(productId);

        this.productId = productId;
    }

    private void validatePositive(Long productId) {
        if (productId <= 0) {
            throw new ShoppingException("productId는 양의 정수입니다.");
        }
    }

    private void validateNotNull(Long productId) {
        if (productId == null) {
            throw new ShoppingException("productId는 필수 항목입니다.");
        }
    }

    public Long getProductId() {
        return productId;
    }
}
