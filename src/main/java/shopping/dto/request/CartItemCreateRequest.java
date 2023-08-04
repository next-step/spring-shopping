package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

public class CartItemCreateRequest {

    private final Long productId;

    @JsonCreator
    public CartItemCreateRequest(@JsonProperty("productId") Long productId) {
        validate(productId);
        this.productId = productId;
    }

    private void validate(Long productId) {
        Assert.notNull(productId, "상품 아이디는 null일수 없습니다.");
    }

    public Long getProductId() {
        return productId;
    }
}
