package shopping.product.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import shopping.common.PurgoMalumVerifier
import shopping.product.dto.response.ProductListResponseBody
import shopping.product.dto.request.ProductRequestBody
import shopping.product.dto.response.ProductResponseBody
import shopping.product.exception.ProductNotFoundException
import shopping.product.service.ProductService

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val purgoMalumVerifier: PurgoMalumVerifier,
    private val productService: ProductService,
) {
    @GetMapping
    fun getProducts(): ProductListResponseBody =
        ProductListResponseBody(
            productService
                .findAll()
                .map(ProductResponseBody::from),
        )

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: Long,
    ): ProductResponseBody {
        val found = productService.findById(id) ?: throw ProductNotFoundException(id)
        return ProductResponseBody.from(found)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(
        @RequestBody @Valid requestBody: ProductRequestBody,
    ): ProductResponseBody {
        purgoMalumVerifier.containsProfanity(requestBody.name)
        val saved = productService.create(requestBody.toEntity())
        return ProductResponseBody.from(saved)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody @Valid requestBody: ProductRequestBody,
    ): ProductResponseBody {
        purgoMalumVerifier.containsProfanity(requestBody.name)
        val saved = productService.update(id, requestBody.toEntity())
        return ProductResponseBody.from(saved)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): String {
        productService.deleteById(id)
        return "Product with id $id deleted successfully."
    }
}
