package shopping.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.dto.response.OrderHistoryResponse;
import shopping.dto.response.OrderResponse;
import shopping.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestAttribute final Long loginMemberId) {
        final OrderResponse orderResponse = orderService.createOrder(loginMemberId);

        return ResponseEntity.created(URI.create("/orders/" + orderResponse.getOrderId())).body(orderResponse);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable final Long orderId) {
        final OrderResponse orderResponse = orderService.readOrderDetail(orderId);

        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderHistoryResponse>> getOrderHistories(@RequestAttribute final Long loginMemberId) {
        final List<OrderHistoryResponse> orderHistoryResponses = orderService.readOrderHistories(loginMemberId);

        return ResponseEntity.ok(orderHistoryResponses);
    }
}
