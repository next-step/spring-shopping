package shopping.controller

import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shopping.controller.dto.ProductRequest
import shopping.service.ProductService

@Validated
@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productService: ProductService,
) {
    @PostMapping
    fun save(
        @Valid @RequestBody productRequest: ProductRequest,
    ) = productService.save(productRequest)

    @GetMapping("/{productId}")
    fun get(
        @PathVariable productId: Long,
    ) = productService.getById(productId)

    @PutMapping("/{productId}")
    fun update(
        @PathVariable productId: Long,
        @Valid @RequestBody productRequest: ProductRequest,
    ) = productService.update(productId, productRequest)

    @DeleteMapping("/{productId}")
    fun delete(
        @PathVariable productId: Long,
    ) = productService.delete(productId)
}
