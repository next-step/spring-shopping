package shopping.order.dto.request;

import java.util.List;

public class OrderCreationRequest {

    private List<Long> cartItemIds;

    private OrderCreationRequest() {
    }

    public OrderCreationRequest(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
}
