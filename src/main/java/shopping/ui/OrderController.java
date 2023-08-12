package shopping.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.application.ExchangeRateService;
import shopping.application.OrderService;
import shopping.domain.vo.ExchangeRate;
import shopping.dto.OrderResponse;
import shopping.ui.config.AuthenticationPrincipal;

import java.net.URI;
import java.util.List;

import static shopping.infrastructure.CurrencyCountry.USA;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ExchangeRateService exchangeRateService;

    public OrderController(final OrderService orderService, final ExchangeRateService exchangeRateService) {
        this.orderService = orderService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal final Long userId) {
        final ExchangeRate exchangeRate = exchangeRateService.convert(USA);
        final Long orderId = orderService.create(userId, exchangeRate);
        return ResponseEntity.created(URI.create("/api/orders/" + orderId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> find(@PathVariable final Long id,
                                              @AuthenticationPrincipal final Long userId) {
        final OrderResponse response = orderService.find(id, userId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(@AuthenticationPrincipal final Long userId) {
        final List<OrderResponse> response = orderService.findAll(userId);
        return ResponseEntity.ok().body(response);
    }
}
