package shopping.order.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopping.order.app.api.OrderUseCase;
import shopping.order.app.api.request.OrderRequest;

@RestController
public class OrderController {

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> orderCartProducts(@RequestBody OrderRequest orderRequest) {
        orderUseCase.order(orderRequest);
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, "/order-history")
                .build();
    }
}
