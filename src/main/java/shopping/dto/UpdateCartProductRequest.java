package shopping.dto;

public class UpdateCartProductRequest {

    private int quantity;

    private UpdateCartProductRequest() {
    }

    public UpdateCartProductRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
