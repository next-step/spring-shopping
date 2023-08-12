package shopping.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.argumentresolver.annotation.UserId;
import shopping.domain.CurrencyConversion;
import shopping.dto.response.OrderIdResponse;
import shopping.dto.response.OrderResponse;
import shopping.service.CurrencyService;
import shopping.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CurrencyService currencyService;

    public OrderController(OrderService orderService, CurrencyService currencyService) {
        this.orderService = orderService;
        this.currencyService = currencyService;
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<OrderIdResponse> orderCartItem(@UserId Long userId) {
        double currency = currencyService.getCurrencyOf(CurrencyConversion.USD_KRW);
        OrderIdResponse orderIdResponse = orderService.orderCartItem(currency, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderIdResponse);
    }

    @GetMapping("/order/{orderId}")
    @ResponseBody
    public ResponseEntity<OrderResponse> findOrder(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.findOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @GetMapping("/order")
    @ResponseBody
    public ResponseEntity<List<OrderResponse>> findOrders(@UserId Long userId) {
        List<OrderResponse> orderResponses = orderService.findOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponses);
    }

    @GetMapping("/order-detail/{orderId}")
    public String orederDetailPage(Model model, @PathVariable Long orderId) {
        model.addAttribute("orderId", orderId);
        return "order-detail";
    }

    @GetMapping("/order-history")
    public String orederHistoryPage() {
        return "order-history";
    }
}
