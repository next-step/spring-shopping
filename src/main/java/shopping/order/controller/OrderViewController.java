package shopping.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderViewController {

    @GetMapping("/order-history")
    public String orderView() {
        return "order-history";
    }

    @GetMapping("/orders/{orderId}")
    public String cartHome(Model model, @PathVariable Long orderId) {
        model.addAttribute("orderId", orderId);
        return "order-detail";
    }

}
