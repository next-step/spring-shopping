package shopping.order.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.annotation.AuthMember;
import shopping.auth.domain.LoggedInMember;
import shopping.order.dto.response.OrderResponse;
import shopping.order.service.OrderExchangeRateService;
import shopping.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderExchangeRateService orderExchangeRateService;

    public OrderController(OrderService orderService,
        OrderExchangeRateService orderExchangeRateService) {
        this.orderService = orderService;
        this.orderExchangeRateService = orderExchangeRateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse addOrder(@AuthMember LoggedInMember loggedInMember) {
        return orderExchangeRateService.createOrder(loggedInMember);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@AuthMember LoggedInMember loggedInMember, @PathVariable Long orderId) {
        return orderService.findOrderById(loggedInMember, orderId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(@AuthMember LoggedInMember loggedInMember) {
        return orderService.findAllOrder(loggedInMember);
    }
}
