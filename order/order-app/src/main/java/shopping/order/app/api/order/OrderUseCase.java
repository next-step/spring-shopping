package shopping.order.app.api.order;

import shopping.order.app.api.order.request.OrderRequest;

public interface OrderUseCase {

    void order(OrderRequest orderRequest);
}
