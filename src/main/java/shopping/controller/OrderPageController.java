package shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderPageController {

    @GetMapping("/{orderId}")
    public String renderOrderDetails(final Model model, @PathVariable final Long orderId) {
        model.addAttribute("orderId", orderId);
        return "order-detail";
    }

    @GetMapping
    public String renderOrdersPage() {
        return "order-history";
    }

}
