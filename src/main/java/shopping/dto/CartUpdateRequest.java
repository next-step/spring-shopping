package shopping.dto;

public class CartUpdateRequest {
    private int quantity;

    private CartUpdateRequest() {
    }

    public CartUpdateRequest(int quantity) {
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }
}
