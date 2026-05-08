package shopping

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class ProductRestController(
    private val productService: ProductService
) {
    @GetMapping("/api/products")
    fun getProducts(): List<ProductResponse> {
        return productService.genProducts()
    }

    @PostMapping("/api/products")
    fun addProducts(@RequestBody request: ProductRequest): ResponseEntity<Unit> {
        val response = productService.addProduct(request)
        return ResponseEntity.created(URI.create("/api/products/${response.id}")).build()
    }
}