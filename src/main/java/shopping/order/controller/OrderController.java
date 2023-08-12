package shopping.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shopping.auth.annotation.AuthMember;
import shopping.auth.domain.LoggedInMember;
import shopping.order.dto.response.OrderResponse;
import shopping.order.service.ExchangeRateService;
import shopping.order.service.ExchangeType;
import shopping.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final ExchangeRateService exchangeRateService;

    public OrderController(OrderService orderService, ExchangeRateService exchangeRateService) {
        this.orderService = orderService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse addOrder(@AuthMember LoggedInMember loggedInMember) {
        return orderService.createOrder(loggedInMember, exchangeRateService.getExchangeRate(
            ExchangeType.USD, ExchangeType.KRW));
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
