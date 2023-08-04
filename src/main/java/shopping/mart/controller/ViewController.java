package shopping.mart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/cart")
    public String cartView() {
        return "cart";
    }
}
