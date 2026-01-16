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
import shopping.controller.model.ProductRequestBody
import shopping.controller.model.ProductResponseBody
import shopping.exception.NotFoundException
import shopping.service.ProductService
import shopping.service.PurgoMalumService

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService,
    private val purgoMalumService: PurgoMalumService,
) {
    @GetMapping
    fun getProducts(): List<ProductResponseBody> =
        productService
            .findAll()
            .map(ProductResponseBody::from)

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: Long,
    ): ResponseEntity<ProductResponseBody> {
        val found = productService.findById(id) ?: throw NotFoundException()
        return ResponseEntity.ok(ProductResponseBody.from(found))
    }

    @PostMapping
    fun createProduct(
        @RequestBody @Valid requestBody: ProductRequestBody,
    ): ProductResponseBody {
        validate(requestBody)
        val saved = productService.create(requestBody.toEntity())
        return ProductResponseBody.from(saved)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody @Valid requestBody: ProductRequestBody,
    ): ResponseEntity<ProductResponseBody> {
        validate(requestBody)
        val saved = productService.update(id, requestBody.toEntity())
        return ResponseEntity.ok(ProductResponseBody.from(saved))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        productService.deleteById(id)
        return ResponseEntity.ok("Product with id $id deleted successfully.")
    }

    private fun validate(product: ProductRequestBody) {
        if (purgoMalumService.containsProfanity(product.name)) {
            throw IllegalArgumentException("Product name contains profanity.")
        }
    }
}
