package shopping.mart.controller;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
    public String findAllProducts(Model model, @PageableDefault(direction = Direction.DESC) Pageable pageable) {
        List<ProductResponse> response = productService.findAllProducts(pageable);
        model.addAttribute("products", response);
        return "index";
    }
}
