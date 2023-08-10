package shopping.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderViewController {

    @GetMapping("/order-history")
    public String getOrderHistoryPage() {
        return "order-history";
    }

    @GetMapping("/order-detail/{orderId}")
    public String getOrderDetailPage(
        @PathVariable Long orderId,
        final Model model
    ) {
        model.addAttribute("orderId", orderId);

        return "order-detail";
    }
}
