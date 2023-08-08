package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderResponse;
import shopping.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> readOrder(@PathVariable Long orderId) {
        final OrderResponse orderResponse = orderService.readOrder(orderId);

        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestAttribute final Long loginMemberId) {
        final OrderCreateResponse orderCreateResponse = orderService.createOrder(loginMemberId);

        return ResponseEntity.ok(orderCreateResponse);
    }
}
