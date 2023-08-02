package shopping.dto;

public class CartItemUpdateRequest {

    private Integer quantity;

    public CartItemUpdateRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public CartItemUpdateRequest() {
    }

    public Integer getQuantity() {
        return quantity;
    }
}
