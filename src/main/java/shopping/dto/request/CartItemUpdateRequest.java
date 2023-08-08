package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateNumberArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemUpdateRequest {

    private static final String QUANTITY_NAME = "quantity";

    private final Integer quantity;

    @JsonCreator
    public CartItemUpdateRequest(@JsonProperty(QUANTITY_NAME) final Integer quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        validateNumberArgument(quantity, QUANTITY_NAME);
    }

    public Integer getQuantity() {
        return quantity;
    }
}
