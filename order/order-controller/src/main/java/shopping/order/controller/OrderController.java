package shopping.order.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.app.api.Token;
import shopping.core.util.ErrorTemplate;
import shopping.order.app.api.order.OrderUseCase;
import shopping.order.app.api.order.request.OrderRequest;
import shopping.order.app.exception.EmptyOrderException;

@RestController
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final Token token;

    public OrderController(OrderUseCase orderUseCase,
            Token token) {
        this.orderUseCase = orderUseCase;
        this.token = token;
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> orderCartProducts() {
        OrderRequest orderRequest = new OrderRequest(Long.parseLong(token.decrypted()));
        long receiptId = orderUseCase.order(orderRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, "/order-detail/" + receiptId)
                .build();
    }

    @ExceptionHandler(EmptyOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorTemplate handleEmptyCartException(EmptyOrderException emptyCartException) {
        return new ErrorTemplate(emptyCartException.getMessage());
    }

}
