package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderResponse;
import shopping.dto.response.OrderResponses;
import shopping.service.ExchangeRateService;
import shopping.service.OrderService;

import static java.time.LocalDateTime.now;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final ExchangeRateService exchangeRateService;

    public OrderController(final OrderService orderService, final ExchangeRateService exchangeRateService) {
        this.orderService = orderService;
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> readOrder(@PathVariable Long orderId) {
        final OrderResponse orderResponse = orderService.readOrder(orderId);

        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<OrderResponses> readOrders(@RequestAttribute final Long loginMemberId) {
        final OrderResponses orderResponses = orderService.readOrders(loginMemberId);

        return ResponseEntity.ok(orderResponses);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestAttribute final Long loginMemberId) {
        final double exchangeRate = exchangeRateService.getExchangeRateEveryMinute(now());
        final OrderCreateResponse orderCreateResponse = orderService.createOrder(loginMemberId, exchangeRate);

        return ResponseEntity.ok(orderCreateResponse);
    }
}
