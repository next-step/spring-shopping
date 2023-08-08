package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.OrderService;
import shopping.auth.UserIdPrincipal;
import shopping.dto.response.OrderResponse;

import java.net.URI;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@UserIdPrincipal Long userId) {
        OrderResponse orderResponse = orderService.createOrder(userId);
        return ResponseEntity.created(URI.create("/order/" + orderResponse.getId())).body(orderResponse);
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderResponse>> getOrder(@UserIdPrincipal Long userId) {
        List<OrderResponse> orderResponses = orderService.findAllByUserId(userId);
        return ResponseEntity.ok().body(orderResponses);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> getOrderDetail(@UserIdPrincipal Long userId, @PathVariable Long id) {
        OrderResponse orderResponse = orderService.findOrderById(userId, id);
        return ResponseEntity.ok().body(orderResponse);
    }
}
