package shopping

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @PostMapping("/api/product")
    fun addProducts(@RequestBody @Valid request: ProductRequest): ResponseEntity<Unit> {
        val response = productService.addProduct(request)
        return ResponseEntity.created(URI.create("/api/products/${response.id}")).build()
    }

    @PutMapping("/api/product/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid request: ProductRequest): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.update(id, request))
    }

    @DeleteMapping("/api/product/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }
}