package shopping.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/cart")
public class CartViewController {

    @GetMapping
    public String cartHome() {
        return "cart";
    }
}