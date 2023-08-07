package shopping.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.application.ProductService;

@Controller
public class PageController {

    private final ProductService productService;

    public PageController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String mainPage(final Model model) {
        model.addAttribute("products", productService.findAllProducts());

        return "index";
    }
}

