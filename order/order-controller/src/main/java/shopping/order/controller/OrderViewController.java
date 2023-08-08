package shopping.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderViewController {

    @GetMapping("/order-detail/{receiptId}")
    public String orderDetailPage(@PathVariable("receiptId") Long receiptId, Model model) {
        model.addAttribute("receiptId", receiptId);
        return "order-detail";
    }

    @GetMapping("/order-history")
    public String orderHistoryPage() {
        return "order-history";
    }

}
