package shopping.web.product

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shopping.application.product.ProductService

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
        @RequestBody productCreateRequest: ProductCreateRequest,
    ): ProductResponse {
        return productService.save(productCreateRequest.toDomain())
    }

    @PutMapping("/{id}")
    fun update(
        @RequestBody productUpdateRequest: ProductUpdateRequest,
    ): ProductResponse {
        return productService.save(productUpdateRequest.toDomain())
    }

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable("id") id: Long,
    ): Boolean {
        return productService.deleteById(id)
    }
}
