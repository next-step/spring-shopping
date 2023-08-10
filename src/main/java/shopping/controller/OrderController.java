package shopping.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.LoginUser;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderDetailResponse;
import shopping.dto.response.OrderHistoryResponse;
import shopping.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@LoginUser final Long memberId) {
        final OrderCreateResponse response = orderService.createOrder(memberId);

        return ResponseEntity.created(URI.create("/order-detail/" + response.getOrderId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderHistoryResponse>> findOrderHistory(
        @LoginUser final Long memberId
    ) {
        return ResponseEntity.ok().body(orderService.findOrderHistory(memberId));
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> findOrderDetail(
        @LoginUser final Long memberId,
        @PathVariable Long orderId
    ) {
        return ResponseEntity.ok().body(orderService.findOrderDetail(memberId, orderId));
    }
}
