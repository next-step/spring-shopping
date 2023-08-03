package shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartProductController {

    @GetMapping("/cart")
    public String getAllCartProductsPage() {
        return "cart";
    }
}
