package shopping.product

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

const val PRODUCT_PATH: String = "/api/products"

@RestController
class ProductRestController(
    private val productService: ProductService,
) {
    @GetMapping(PRODUCT_PATH)
    fun getProducts(): List<ProductResponse> = emptyList()

    @GetMapping("$PRODUCT_PATH/{id}")
    fun getProduct(
        @PathVariable id: Long,
    ): ProductResponse = productService.getProduct(id)

    @PostMapping(PRODUCT_PATH)
    fun addProduct(
        @RequestBody request: ProductRequest,
    ): ResponseEntity<Unit> {
        val product = productService.addProduct(request)
        return ResponseEntity.created(URI("$PRODUCT_PATH/${product.id}")).build()
    }

    @PutMapping("$PRODUCT_PATH/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: ProductRequest,
    ): ResponseEntity<Unit> {
        productService.updateProduct(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("$PRODUCT_PATH/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity<Unit> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}
