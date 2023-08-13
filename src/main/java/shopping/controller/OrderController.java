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
import shopping.dto.request.OrderExchangeRequest;
import shopping.dto.response.OrderDetailResponse;
import shopping.dto.response.OrderHistoryResponse;
import shopping.exchange.CurrencyType;
import shopping.service.OrderExchangeService;
import shopping.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderExchangeService orderExchangeService;

    public OrderController(
        final OrderService orderService,
        final OrderExchangeService orderExchangeService
    ) {
        this.orderService = orderService;
        this.orderExchangeService = orderExchangeService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@LoginUser final Long memberId) {
        final Long orderId = orderService.createOrder(memberId);

        return ResponseEntity.created(URI.create("/order-detail/" + orderId)).build();
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
        final OrderExchangeRequest request =
            new OrderExchangeRequest(
                memberId,
                orderId,
                CurrencyType.USD,
                CurrencyType.KRW
            );

        return ResponseEntity.ok()
            .body(orderExchangeService.findOrderDetailWithCurrencyExchangeRate(request));
    }
}
