package shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.dto.response.ProductResponses;
import shopping.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String readProducts(final Model model) {
        final ProductResponses productResponses = productService.readProducts();
        model.addAttribute("products", productResponses.getProducts());

        return "index";
    }
}
