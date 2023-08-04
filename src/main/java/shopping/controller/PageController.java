package shopping.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shopping.application.ProductService;
import shopping.dto.response.ProductResponse;

@Controller
public class PageController {

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") Integer page, Model model) {
        Page<ProductResponse> products = productService.findAllByPage(page);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "cart";
    }
}
