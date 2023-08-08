package shopping.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public void orderCartProducts(@RequestBody OrderRequest orderRequest) {
        orderUseCase.order(orderRequest);
    }

}
