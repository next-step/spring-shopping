package shopping.order.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.service.interceptor.TokenPerRequest;
import shopping.core.util.ErrorTemplate;
import shopping.order.app.api.OrderUseCase;
import shopping.order.app.api.request.OrderRequest;
import shopping.order.app.domain.exception.EmptyCartException;

@RestController
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final TokenPerRequest tokenPerRequest;

    public OrderController(OrderUseCase orderUseCase,
            TokenPerRequest tokenPerRequest) {
        this.orderUseCase = orderUseCase;
        this.tokenPerRequest = tokenPerRequest;
    }

    @GetMapping("/orders")
    public ResponseEntity<Void> orderCartProducts() {
        OrderRequest orderRequest = new OrderRequest(Long.parseLong(tokenPerRequest.getDecryptedToken()));
        orderUseCase.order(orderRequest);
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, "/order-history")
                .build();
    }

    @ExceptionHandler(EmptyCartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorTemplate handleEmptyCartException(EmptyCartException emptyCartException) {
        return new ErrorTemplate(emptyCartException.getMessage());
    }
}
