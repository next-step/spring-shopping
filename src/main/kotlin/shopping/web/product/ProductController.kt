package shopping.web.product

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shopping.application.product.ProductService
import shopping.web.product.request.ProductRequest
import shopping.web.product.response.ProductResponse
import shopping.web.product.response.ProductsResponse

@RestController
@RequestMapping("/api/products")
class ProductController(val productService: ProductService) {
    @GetMapping
    fun findAll(): ProductsResponse {
        return productService.findAll()
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Long,
    ): ProductResponse {
        return productService.findById(id)
    }

    @PostMapping
    fun create(
        @Valid @RequestBody productRequest: ProductRequest,
    ): ProductResponse {
        return productService.save(productRequest.toDomain())
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody productRequest: ProductRequest,
    ): ProductResponse {
        return productService.save(productRequest.toDomain(id))
    }

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable("id") id: Long,
    ) {
        productService.deleteById(id)
    }
}
