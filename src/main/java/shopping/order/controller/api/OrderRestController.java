package shopping.order.controller.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.dto.LoginUser;
import shopping.order.dto.OrderResponse;
import shopping.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(final @LoginUser Long memberId) {
        return ResponseEntity.ok().body(orderService.saveOrder(memberId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderByMember(final @LoginUser Long memberId) {
        return ResponseEntity.ok().body(orderService.getOrderList(memberId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
        final @LoginUser Long memberId,
        final @PathVariable Long orderId) {
        OrderResponse order = orderService.getOrder(memberId, orderId);
        System.out.println(order.getExchangeRate());
        return ResponseEntity.ok().body(order);
    }
}
