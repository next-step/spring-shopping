package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.UserIdPrincipal;
import shopping.dto.response.OrderResponse;

import java.net.URI;

@RestController
public class OrderController {

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@UserIdPrincipal Long userId) {
        return ResponseEntity.created(URI.create("/order/")).build();
    }
}
