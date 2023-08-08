package shopping.order.controller;

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
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@LoginUser Long memberId) {
        return ResponseEntity.ok().body(orderService.saveOrder(memberId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderByMember (@LoginUser Long memberId){
        return  ResponseEntity.ok().body(orderService.getOrderList(memberId));
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
        @LoginUser Long memberId,
        @PathVariable Long orderId)
    {
        return ResponseEntity.ok().body(orderService.getOrder(memberId, orderId));
    }
}
