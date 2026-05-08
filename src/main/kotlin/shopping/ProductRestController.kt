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
import shopping.product.ProductRequest
import shopping.product.ProductResponse
import shopping.product.ProductService
import java.net.URI

const val PRODUCT_PATH: String = "/api/products"

@RestController
class ProductRestController(
    private val productService: ProductService
) {
    @GetMapping(PRODUCT_PATH)
    fun getProducts(): List<ProductResponse> {
        return productService.getProducts()
    }
    @GetMapping("$PRODUCT_PATH/{id}")
    fun getProduct(@PathVariable id: Long): ProductResponse {
        return productService.getProduct(id)
    }
    @PostMapping(PRODUCT_PATH)
    fun addProduct(@RequestBody @Valid request: ProductRequest): ResponseEntity<Unit> {
        val response = productService.addProduct(request)
        return ResponseEntity.created(URI.create("$PRODUCT_PATH/${response.id}")).build()
    }
    @PutMapping("$PRODUCT_PATH/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody @Valid request: ProductRequest): ResponseEntity<Unit> {
        productService.updateProduct(id, request)
        return ResponseEntity.noContent().build()
    }
    @DeleteMapping("$PRODUCT_PATH/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Unit> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}