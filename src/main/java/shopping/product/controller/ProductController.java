package shopping.product.controller;

import static shopping.util.ValidUtils.validNotEmpty;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shopping.product.dto.request.ProductCreationRequest;
import shopping.product.dto.request.ProductUpdateRequest;
import shopping.product.dto.response.ProductResponse;
import shopping.product.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String findAllProducts(Model model) {
        List<ProductResponse> productResponses = productService.readAllProduct();
        model.addAttribute("products", productResponses);
        return "index";
    }

    @GetMapping("/products/{productId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse findProduct(@PathVariable Long productId) {
        return productService.readProduct(productId);
    }

    @PostMapping("/products")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse createProduct(@RequestBody ProductCreationRequest productCreationRequest) {
        validCreationRequest(productCreationRequest);
        return productService.createProduct(productCreationRequest);
    }

    private void validCreationRequest(ProductCreationRequest productCreationRequest) {
        validNotEmpty(productCreationRequest.getImageUrl());
        validNotEmpty(productCreationRequest.getName());
        validNotEmpty(productCreationRequest.getPrice());
    }

    @PutMapping("/products/{productId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(
        @RequestBody ProductUpdateRequest productUpdateRequest,
        @PathVariable Long productId
    ) {
        validUpdateRequest(productUpdateRequest);
        productService.updateProduct(productUpdateRequest, productId);
    }

    private void validUpdateRequest(ProductUpdateRequest productUpdateRequest) {
        validNotEmpty(productUpdateRequest.getName());
        validNotEmpty(productUpdateRequest.getPrice());
        validNotEmpty(productUpdateRequest.getImageUrl());
    }

    @DeleteMapping("/products/{productId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
