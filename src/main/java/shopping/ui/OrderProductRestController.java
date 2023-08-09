package shopping.ui;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.OrderProductService;
import shopping.dto.OrderDetailResponse;
import shopping.ui.argumentresolver.Login;

@RestController
@RequestMapping("/order")
public class OrderProductRestController {

    private final OrderProductService orderProductService;

    public OrderProductRestController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @PostMapping
    public ResponseEntity<Void> orderProduct(@Login Long memberId) {
        long orderId = orderProductService.orderProduct(memberId);
        return ResponseEntity.ok()
            .header(HttpHeaders.LOCATION, "/order-detail/" + orderId)
            .build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> findOrder(@PathVariable long orderId) {
        return ResponseEntity.ok().body(orderProductService.findOrder(orderId));
    }
}
