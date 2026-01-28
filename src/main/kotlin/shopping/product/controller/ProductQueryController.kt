package shopping.product.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shopping.product.dto.response.ProductListResponseBody
import shopping.product.dto.response.ProductResponseBody
import shopping.product.service.ProductQueryService

@RestController
@RequestMapping("/api/products")
class ProductQueryController(
    private val productQueryService: ProductQueryService,
) {
    @GetMapping
    fun getProducts(): ProductListResponseBody =
        ProductListResponseBody(
            productQueryService
                .findAll()
                .map(ProductResponseBody::from),
        )

    @GetMapping("/{id}")
    fun getProduct(
        @PathVariable id: Long,
    ): ProductResponseBody {
        val found = productQueryService.getById(id)
        return ProductResponseBody.from(found)
    }
}
