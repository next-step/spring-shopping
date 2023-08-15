package shopping.cart.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.auth.argumentresolver.annotation.UserId;
import shopping.cart.component.ExchangeRateProvider;
import shopping.cart.dto.response.OrderDetailResponse;
import shopping.cart.dto.response.OrderResponse;
import shopping.cart.service.OrderService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final ExchangeRateProvider exchangeRateProvider;

    public OrderController(final OrderService orderService, final ExchangeRateProvider exchangeRateProvider) {
        this.orderService = orderService;
        this.exchangeRateProvider = exchangeRateProvider;
    }

    @PostMapping
    public ResponseEntity<Void> order(@UserId Long userId) {
        final OrderResponse orderResponse = orderService.order(userId, exchangeRateProvider.fetchExchangeRate());
        return ResponseEntity.created(
                URI.create("/order/" + orderResponse.getOrderId())).build();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailResponse getOrderDetail(@PathVariable Long orderId, @UserId Long userId) {
        return orderService.getOrderDetail(orderId, userId);
    }
}
