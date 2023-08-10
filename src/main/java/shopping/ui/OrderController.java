package shopping.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.OrderService;
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
}
