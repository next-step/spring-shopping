package shopping.order.app.api.request;

public final class OrderRequest {

    private long userId;

    public OrderRequest() {
    }

    public OrderRequest(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
