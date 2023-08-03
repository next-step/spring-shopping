package shopping.controller;

import org.springframework.web.bind.annotation.RestController;
import shopping.application.ProductService;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // TODO: 상품 추가 API?

}
