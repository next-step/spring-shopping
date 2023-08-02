package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemUpdateRequest {

    private static final int MIN_QUANTITY = 1;
    private Integer quantity;

    @JsonCreator
    public CartItemUpdateRequest(@JsonProperty("quantity") Integer quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("수량은 1이상이어야 합니다.");
        }
    }

    public Integer getQuantity() {
        return quantity;
    }
}
