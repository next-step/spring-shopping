package shopping.mart.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.mart.dto.ProductResponse;
import shopping.mart.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;

    ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String findAllProducts(Model model) {
        List<ProductResponse> response = productService.findAllProducts();
        model.addAttribute("products", response);
        return "index";
    }
}
