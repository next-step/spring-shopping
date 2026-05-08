package shopping.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shopping.controller.request.ProductRequest
import shopping.controller.response.ProductResponse
import shopping.service.ProductService
import java.net.URI

@RestController
@RequestMapping("/api/products")
class ShoppingController(
    private val productService: ProductService
) {
    @PostMapping
    fun createProduct(@RequestBody request: ProductRequest): ResponseEntity<Unit> {
        val id = productService.addProduct(request)
        return ResponseEntity
            .created(URI.create("$/api/products/${id}"))
            .build()
    }

    @GetMapping
    fun getProducts(): List<ProductResponse> {
        return productService.getProducts()
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): ProductResponse {
        return productService.getProductById(id)
    }

    @PatchMapping("/{id}")
    fun patchProduct(
        @PathVariable id: Long,
        @RequestBody request: ProductRequest
    ): ResponseEntity<Unit> {
        productService.updateProduct(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Unit> {
        productService.deleteProduct(id)
        return ResponseEntity.ok().build()
    }
}