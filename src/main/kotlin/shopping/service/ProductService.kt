package shopping.service

import org.springframework.stereotype.Service
import shopping.controller.dto.ProductRequest
import shopping.controller.dto.ProductResponse
import shopping.domain.BadWordValidator
import shopping.domain.Product
import shopping.repository.ProductRepository

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val badWordValidator: BadWordValidator,
) {
    fun save(request: ProductRequest): Long {
        validateBadWord(request.name)
        return productRepository.save(Product(request.name, request.price, request.imageUrl))
    }

    fun getById(id: Long) = productRepository.getById(id).let { ProductResponse(it.name, it.price, it.imageUrl) }

    fun update(
        id: Long,
        request: ProductRequest,
    ) {
        validateBadWord(request.name)
        productRepository.update(id, Product(request.name, request.price, request.imageUrl))
    }

    fun delete(id: Long): Unit = productRepository.delete(id)

    private fun validateBadWord(name: String) {
        require(badWordValidator.notContainsBadWord(name)) { "상품 이름은 비속어를 포함할 수 없습니다. 이름 : $name" }
    }
}
