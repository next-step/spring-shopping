package shopping.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import shopping.controller.dto.ProductRequest
import shopping.service.ProductService

@RestController("/v1/products")
class ProductController(
    private val productService: ProductService,
) {
    @PostMapping
    fun save(
        @RequestBody productRequest: ProductRequest,
    ) = productService.save(productRequest)

    @GetMapping
    fun get(
        @RequestParam id: Long,
    ) = productService.getById(id)

    @PutMapping
    fun update(
        @RequestParam id: Long,
        @RequestBody productRequest: ProductRequest,
    ) = productService.update(id, productRequest)

    @DeleteMapping
    fun delete(
        @RequestParam id: Long,
    ) = productService.delete(id)
}
