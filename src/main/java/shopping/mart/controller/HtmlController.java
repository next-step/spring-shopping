package shopping.mart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/cart")
    public String cartView() {
        return "cart";
    }
}
