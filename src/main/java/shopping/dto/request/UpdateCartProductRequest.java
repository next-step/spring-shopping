package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import shopping.exception.CartException;

public class UpdateCartProductRequest {

    private int quantity;


    @JsonCreator
    public UpdateCartProductRequest(@JsonProperty int quantity) {
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
