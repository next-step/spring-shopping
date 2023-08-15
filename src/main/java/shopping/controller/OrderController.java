package shopping.controller;

import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.OrderCreator;
import shopping.application.OrderService;
import shopping.auth.EmailFromAccessToken;
import shopping.dto.request.ShoppingPageRequest;
import shopping.dto.response.OrderResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "12";

    private final OrderService orderService;
    private final OrderCreator orderCreator;

    public OrderController(OrderService orderService, OrderCreator orderCreator) {
        this.orderService = orderService;
        this.orderCreator = orderCreator;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@EmailFromAccessToken String email) {
        Long orderId = orderCreator.createOrder(email);
        return ResponseEntity.created(URI.create("/order-history/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> orderInfo(
            @EmailFromAccessToken String email,
            @PathVariable Long orderId) {

        OrderResponse response = orderService.findOrder(email, orderId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> allOrderInfo(
            @EmailFromAccessToken String email,
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer size) {

        Page<OrderResponse> responses = orderService
                .findAllOrder(email, ShoppingPageRequest.recentPageOf(page, size));
        return ResponseEntity.ok().body(responses);
    }
}
