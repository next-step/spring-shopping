package shopping.dto;

public class CartItemQuantityRequest {
    private Long id;
    private int quantity;

    private CartItemQuantityRequest() {
    }

    public CartItemQuantityRequest(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
