package shopping.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.ExchangeRateProviderService;
import shopping.application.OrderService;
import shopping.dto.OrderDetailResponse;
import shopping.dto.OrderResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ExchangeRateProviderService exchangeRateProviderService;

    public OrderController(final OrderService orderService,
                           final ExchangeRateProviderService exchangeRateProviderService) {
        this.orderService = orderService;
        this.exchangeRateProviderService = exchangeRateProviderService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal final Long userId) {
        final double exchangeRate = exchangeRateProviderService.getExchangeRate();
        final Long orderId = orderService.createFromCart(userId, exchangeRate);
        return ResponseEntity.created(URI.create("/order/" + orderId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> findDetail(@AuthenticationPrincipal final Long userId,
                                                          @PathVariable final Long id) {
        return ResponseEntity.ok().body(orderService.findById(userId, id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(@AuthenticationPrincipal final Long userId) {
        return ResponseEntity.ok().body(orderService.findAll(userId));
    }
}
