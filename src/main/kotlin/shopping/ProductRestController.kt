package shopping

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class ProductRestController(
    private val productService: ProductService,
) {
    @GetMapping("/api/products")
    fun getProducts(): List<ProductResponse> = productService.getProducts()

    @PostMapping("/api/products")
    fun addProduct(
        @RequestBody request: ProductRequest,
    ): ResponseEntity<ProductResponse> {
        val response = productService.addProduct(request)
        return ResponseEntity.created(URI.create("/api/products/${response.id}")).body(response)
    }
}
