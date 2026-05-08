package shopping

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductRestController(
    private val productService: ProductService,
) {
    @GetMapping("/api/products")
    fun getProducts(): List<ProductResponse> = productService.getProducts()
}
