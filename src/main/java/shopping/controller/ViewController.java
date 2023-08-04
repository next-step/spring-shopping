package shopping.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.application.ProductService;
import shopping.dto.response.ProductResponse;

import java.util.List;

@Controller
public class ViewController {

    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model, @PageableDefault Pageable pageable) {
        List<ProductResponse> products = productService.findAll(pageable);
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "cart";
    }
}
