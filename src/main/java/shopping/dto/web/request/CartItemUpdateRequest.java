package shopping.dto.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import shopping.exception.general.InvalidRequestException;

public class CartItemUpdateRequest {

    private static final int MIN_QUANTITY = 1;
    private final Integer quantity;

    @JsonCreator
    public CartItemUpdateRequest(@JsonProperty("quantity") Integer quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new InvalidRequestException("수량은 1이상이어야 합니다.");
        }
    }

    public Integer getQuantity() {
        return quantity;
    }
}
