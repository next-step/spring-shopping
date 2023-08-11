package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderResponse;
import shopping.dto.response.OrderResponses;
import shopping.infrastructure.ExchangeRateApi;
import shopping.service.OrderService;

import static java.time.LocalDateTime.now;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final ExchangeRateApi exchangeRateApi;

    public OrderController(final OrderService orderService, final ExchangeRateApi exchangeRateApi) {
        this.orderService = orderService;
        this.exchangeRateApi = exchangeRateApi;
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
        final double exchangeRate = exchangeRateApi.getExchangeRateEveryMinute(now());
        final OrderCreateResponse orderCreateResponse = orderService.createOrder(loginMemberId, exchangeRate);

        return ResponseEntity.ok(orderCreateResponse);
    }
}
