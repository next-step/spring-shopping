package shopping.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OrderViewController {

    @GetMapping("/order/{orderId}")
    public String getOrderDetail(@PathVariable Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "order-detail";
    }

    @GetMapping("/order-history")
    public String getOrders() {
        return "order-history";
    }
}
