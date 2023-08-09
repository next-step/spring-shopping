package shopping.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {

    @GetMapping("/order-detail/{orderId}")
    public String getOrderDetailPage(
        final @PathVariable Long orderId,
        final Model model
    ) {
        model.addAttribute("orderId", orderId);
        return "/order-detail";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage() {
        return "/order-history";
    }
}
