package shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.dto.ProductsGetResponse;
import shopping.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;

    ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String findAllProducts(Model model) {
        ProductsGetResponse response = productService.findAllProducts();
        model.addAttribute("products", response.getProducts());
        return "index";
    }
}
