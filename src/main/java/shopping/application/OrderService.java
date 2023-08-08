package shopping.application;

import org.springframework.stereotype.Service;
import shopping.dto.response.OrderResponse;
import shopping.dto.request.OrderRequest;

@Service
public class OrderService {

    public OrderResponse order(long memberId, OrderRequest orderRequest) {
        return new OrderResponse(1);
    }
}
