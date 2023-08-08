package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import shopping.dto.response.OrderCreateResponse;
import shopping.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestAttribute final Long loginMemberId) {
        final OrderCreateResponse orderCreateResponse = orderService.createOrder(loginMemberId);

        return ResponseEntity.ok(orderCreateResponse);
    }
}
