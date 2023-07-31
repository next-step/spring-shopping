package shopping.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.dto.ProductResponse;

@Controller
public class ProductController {

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> products = List.of();
        model.addAttribute("products", products);
        return "index";
    }
}
