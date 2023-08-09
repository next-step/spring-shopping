package shopping.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderProductController {

    @GetMapping("/order-detail/{orderId}")
    public String findOrder(@PathVariable long orderId) {
        return "order-detail";
    }
}
