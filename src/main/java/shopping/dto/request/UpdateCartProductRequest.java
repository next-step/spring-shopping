package shopping.dto.request;

import shopping.exception.CartException;

public class UpdateCartProductRequest {

    private int quantity;

    private UpdateCartProductRequest() {
    }

    public UpdateCartProductRequest(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new CartException("상품 수량은 0 이하일 수 없습니다");
        }
    }
}
