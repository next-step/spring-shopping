package shopping.order.app.api;

import shopping.order.app.api.request.OrderRequest;

public interface OrderUseCase {

    void order(OrderRequest orderRequest);
}
