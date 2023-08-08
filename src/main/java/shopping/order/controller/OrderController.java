package shopping.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.dto.LoginUser;
import shopping.order.domain.Order;
import shopping.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@LoginUser Long memberId){
        Order order = orderService.saveOrder(memberId);
        return ResponseEntity.ok().body(order);
    }
}
