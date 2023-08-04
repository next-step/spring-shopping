package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

public class CartItemUpdateRequest {

    private static final int MIN_QUANTITY = 1;
    private final Integer quantity;

    @JsonCreator
    public CartItemUpdateRequest(@JsonProperty("quantity") Integer quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        Assert.notNull(quantity, "수량은 null일수 없습니다.");
        Assert.isTrue(quantity >= MIN_QUANTITY, "수량은 1이상이어야 합니다.");
    }

    public Integer getQuantity() {
        return quantity;
    }
}
