package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.LoginUser;
import shopping.dto.response.OrderCreateResponse;
import shopping.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@LoginUser final Long memberId) {
        return ResponseEntity.ok().body(orderService.createOrder(memberId));
    }
}
