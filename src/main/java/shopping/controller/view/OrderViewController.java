package shopping.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderViewController {

    @GetMapping("/order-history")
    public String getOrderHistoryPage() {
        return "order-history";
    }

    @GetMapping("/order-detail/{orderId}")
    public String getOrderDetailPage(
    ) {
        return "order-detail";
    }
}
