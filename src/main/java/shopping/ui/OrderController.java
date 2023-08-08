package shopping.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.OrderService;
import shopping.dto.OrderDetailResponse;
import shopping.dto.OrderResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal Long userId) {
        Long orderId = orderService.create(userId);
        return ResponseEntity.created(URI.create("/order/" + orderId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> findDetail(@AuthenticationPrincipal Long userId,
                                                          @PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.findById(userId, id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok().body(orderService.findAll(userId));
    }
}
