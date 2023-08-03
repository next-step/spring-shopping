package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartProductQuantityUpdateRequest {

    private final int quantity;

    @JsonCreator
    public CartProductQuantityUpdateRequest(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
