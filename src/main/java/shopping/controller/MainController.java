package shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.service.ProductService;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getMainPage(final Model model) {
        model.addAttribute("products", productService.findAllProducts());

        return "index";
    }
}
