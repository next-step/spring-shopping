package shopping.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartPageController {

    @GetMapping("/cart-items")
    public String renderCartPage() {
        return "cart";
    }
}
