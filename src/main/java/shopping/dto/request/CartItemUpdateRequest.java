package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateNumberArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemUpdateRequest {

    private final Integer quantity;

    @JsonCreator
    public CartItemUpdateRequest(@JsonProperty("quantity") Integer quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        validateNumberArgument(quantity, "수량");
    }

    public Integer getQuantity() {
        return quantity;
    }
}
