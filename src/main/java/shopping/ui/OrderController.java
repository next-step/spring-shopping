package shopping.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import shopping.application.OrderService;
import shopping.dto.response.OrderResponse;
import shopping.ui.argumentresolver.Login;

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<Void> order(@Login Long memberId) {
        OrderResponse response = orderService.order(memberId);
        return ResponseEntity
            .created(URI.create("/order-history/" + response.getId()))
            .build();
    }
}
