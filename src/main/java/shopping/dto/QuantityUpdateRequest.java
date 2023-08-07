package shopping.dto;

public class QuantityUpdateRequest {
    private int quantity;

    private QuantityUpdateRequest() {
    }

    public QuantityUpdateRequest(final int quantity) {
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }
}
