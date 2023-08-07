package shopping.dto;

public class CartProductRequest {

    private int quantity;

    private CartProductRequest() {
    }

    public CartProductRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
