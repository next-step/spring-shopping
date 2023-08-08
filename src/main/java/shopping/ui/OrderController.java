package shopping.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.application.OrderService;
import shopping.dto.response.OrderResponse;
import shopping.ui.argumentresolver.Login;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<Void> order(@Login Long memberId) {
        OrderResponse response = orderService.order(memberId);
        return ResponseEntity
            .created(URI.create("/order-detail/" + response.getId()))
            .build();
    }
}
