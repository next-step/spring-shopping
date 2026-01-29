package shopping.service

import org.springframework.stereotype.Service
import shopping.controller.dto.ProductRequest
import shopping.controller.dto.ProductResponse
import shopping.domain.BadWordValidator

@Service
class ProductService(
    private val productTransactionalService: ProductTransactionalService,
    private val badWordValidator: BadWordValidator,
) {
    fun save(request: ProductRequest): Long {
        validateBadWord(request.name)
        return productTransactionalService.save(request)
    }

    fun getById(id: Long): ProductResponse {
        val product = productTransactionalService.findById(id)
        return ProductResponse(product.name, product.price, product.imageUrl)
    }

    fun update(
        id: Long,
        request: ProductRequest,
    ) {
        validateBadWord(request.name)
        productTransactionalService.update(id, request)
    }

    fun delete(id: Long) {
        productTransactionalService.delete(id)
    }

    private fun validateBadWord(name: String) {
        require(badWordValidator.notContainsBadWord(name)) { "상품 이름은 비속어를 포함할 수 없습니다. 이름 : $name" }
    }
}
