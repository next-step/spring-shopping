package shopping.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.application.OrderService;
import shopping.dto.OrderResponse;
import shopping.ui.config.AuthenticationPrincipal;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal final Long userId) {
        final Long orderId = orderService.create(userId);
        return ResponseEntity.created(URI.create("/api/orders/" + orderId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> find(@PathVariable final Long id,
                                              @AuthenticationPrincipal final Long userId) {
        final OrderResponse response = orderService.find(id, userId);
        return ResponseEntity.ok().body(response);
    }
}
