package shopping.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shopping.controller.model.ProductRequest
import shopping.controller.model.ProductResponse
import shopping.service.ProductService
import shopping.service.PurgoMalumService

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService,
    private val purgoMalumService: PurgoMalumService,
) {
    @GetMapping
    fun getProducts(): List<ProductResponse> =
        productService
            .findAll()
            .map(ProductResponse::from)

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: Long,
    ): ResponseEntity<ProductResponse> {
        val found = productService.getById(id)
        return ResponseEntity.ok(ProductResponse.from(found))
    }

    @PostMapping
    fun createProduct(
        @RequestBody @Valid requestBody: ProductRequest,
    ): ProductResponse {
        validate(requestBody)
        val saved = productService.create(requestBody.toEntity())
        return ProductResponse.from(saved)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody @Valid requestBody: ProductRequest,
    ): ResponseEntity<ProductResponse> {
        validate(requestBody)
        val saved = productService.update(id, requestBody.toEntity())
        return ResponseEntity.ok(ProductResponse.from(saved))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        productService.deleteById(id)
        return ResponseEntity.ok("Product with id $id deleted successfully.")
    }

    private fun validate(product: ProductRequest) {
        if (purgoMalumService.containsProfanity(product.name)) {
            throw IllegalArgumentException("Product name contains profanity.")
        }
    }
}
