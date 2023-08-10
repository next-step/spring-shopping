package shopping.order.controller;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.annotation.AuthMember;
import shopping.auth.domain.LoggedInMember;
import shopping.order.dto.request.OrderCreationRequest;
import shopping.order.dto.response.OrderDetailResponse;
import shopping.order.dto.response.OrdersResponse;
import shopping.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> makeOrder(
        @AuthMember LoggedInMember loggedInMember,
        @RequestBody OrderCreationRequest orderCreationRequest
    ) {
        Long orderId = orderService.addOrder(loggedInMember, orderCreationRequest);
        return ResponseEntity.created(URI.create("/order/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailResponse getOrderDetail(
        @AuthMember LoggedInMember loggedInMember,
        @PathVariable Long orderId
    ) {
        return orderService.getOrderDetail(loggedInMember, orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public OrdersResponse getOrders(@AuthMember LoggedInMember loggedInMember) {
        return orderService.getOrders(loggedInMember);
    }
}
