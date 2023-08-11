package shopping.order.domain.usecase.order;

import shopping.order.domain.usecase.order.request.OrderRequest;

public interface OrderUseCase {

    long order(OrderRequest orderRequest);
}
