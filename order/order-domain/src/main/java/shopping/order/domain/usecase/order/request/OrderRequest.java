package shopping.order.domain.usecase.order.request;

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
