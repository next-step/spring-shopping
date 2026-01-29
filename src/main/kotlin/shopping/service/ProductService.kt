package shopping.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.controller.dto.ProductRequest
import shopping.controller.dto.ProductResponse
import shopping.domain.BadWordValidator
import shopping.domain.Product
import shopping.repository.ProductRepository

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository,
    private val badWordValidator: BadWordValidator,
) {
    fun save(request: ProductRequest): Long {
        validateBadWord(request.name)
        return productRepository.save(request.toDomain()).id!!
    }

    @Transactional(readOnly = true)
    fun getById(id: Long): ProductResponse = get(id).let { ProductResponse(it.name, it.price, it.imageUrl) }

    private fun get(id: Long): Product =
        productRepository
            .findById(id)
            .orElseThrow { IllegalStateException("상품을 찾을 수 없습니다. id: $id") }

    fun update(
        id: Long,
        request: ProductRequest,
    ) {
        validateBadWord(request.name)
        val product = get(id)
        product.update(request.toDomain())
    }

    fun delete(id: Long): Unit = productRepository.deleteById(id)

    private fun validateBadWord(name: String) {
        require(badWordValidator.notContainsBadWord(name)) { "상품 이름은 비속어를 포함할 수 없습니다. 이름 : $name" }
    }
}
