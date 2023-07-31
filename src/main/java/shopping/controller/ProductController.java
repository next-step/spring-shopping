package shopping.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shopping.application.ProductService;
import shopping.dto.ProductRequest;
import shopping.dto.ProductResponse;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {
        productService.insert(productRequest);
        return ResponseEntity.created(URI.create("/")).build();
    }
}
