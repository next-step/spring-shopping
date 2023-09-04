package shopping.mart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.mart.dto.OrderDetailResponse;
import shopping.mart.service.OrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> order(@RequestAttribute("userId") Long userId) {
        String location = "/orders/" + orderService.order(userId);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDetailResponse> findOrderHistory(@RequestAttribute("userId") Long userId) {
        return orderService.findOrderHistory(userId);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailResponse findOrderDetail(@RequestAttribute("userId") Long userId, @PathVariable Long orderId) {
        return orderService.findOrderDetail(userId, orderId);
    }
}
