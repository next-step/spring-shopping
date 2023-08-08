package shopping.dto;

import shopping.domain.entity.Quantity;

public class QuantityUpdateRequest {

    private Quantity quantity;

    private QuantityUpdateRequest() {
    }

    public QuantityUpdateRequest(final int quantity) {
        this.quantity = new Quantity(quantity);
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
