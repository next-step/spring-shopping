package shopping.dto;

public class CartItemQuantityRequest {
    private int quantity;

    private CartItemQuantityRequest() {
    }

    public CartItemQuantityRequest(int quantity) {
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }
}
