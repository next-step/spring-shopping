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
import shopping.dto.response.OrderIdResponse;
import shopping.dto.response.OrderResponse;
import shopping.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<OrderIdResponse> orderCartItem(@UserId Long userId) {
        OrderIdResponse orderIdResponse = orderService.orderCartItem(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderIdResponse);
    }

    @GetMapping("/order/{orderId}")
    @ResponseBody
    public ResponseEntity<OrderResponse> findOrder(@PathVariable Long orderId, @UserId Long userId) {
        OrderResponse orderResponse = orderService.findOrder(orderId, userId);
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
