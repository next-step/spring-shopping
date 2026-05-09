package shopping.api

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shopping.dto.ProductRequest
import shopping.dto.ProductResponse
import shopping.service.ProductService
import java.net.URI

@RestController
class ProductApi(
    private val productService: ProductService) {

    companion object {
        const val PRODUCTS_URL = "/api/products"
        const val PRODUCT_URL = "/api/product"
    }

    @GetMapping(PRODUCTS_URL)
    fun getProducts(): ResponseEntity<List<ProductResponse>> {
        val result = productService.getProducts()
        return ResponseEntity.ok(
            result.map(::ProductResponse)
        )
    }

    @GetMapping("${PRODUCT_URL}/{id}")
    fun getProduct(@PathVariable id: Long): ResponseEntity<ProductResponse> {
        val result = productService.getProduct(id)
        return ResponseEntity.ok(ProductResponse(result))
    }

    @PostMapping(PRODUCT_URL)
    fun addProduct(@RequestBody @Valid request: ProductRequest): ResponseEntity<ProductResponse> {
        val result = productService.addProduct(request)
        return ResponseEntity.created(URI.create("${PRODUCT_URL}/${result.id}"))
            .body(ProductResponse(result))
    }

    @PutMapping("${PRODUCT_URL}/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid request: ProductRequest): ResponseEntity<ProductResponse> {
        val result = productService.update(id, request)
        return ResponseEntity.ok(ProductResponse(result))
    }

    @DeleteMapping("${PRODUCT_URL}/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }
}