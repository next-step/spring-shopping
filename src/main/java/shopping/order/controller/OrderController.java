package shopping.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    @GetMapping("/order-detail")
    public String getOrderDetailPage(@RequestParam Long orderId, Model model){
        model.addAttribute("orderId", orderId);
        return "/order-detail";
    }
}
