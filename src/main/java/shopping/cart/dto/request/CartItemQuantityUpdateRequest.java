package shopping.cart.dto.request;

public class CartItemQuantityUpdateRequest {

    private Integer quantity;

    private CartItemQuantityUpdateRequest() {
    }

    public CartItemQuantityUpdateRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
