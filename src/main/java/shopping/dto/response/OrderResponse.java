package shopping.dto.response;

public class OrderResponse {

    private long id;

    private OrderResponse() {
    }

    public OrderResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
