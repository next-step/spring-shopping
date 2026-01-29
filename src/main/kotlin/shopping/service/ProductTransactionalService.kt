package shopping.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.controller.dto.ProductRequest
import shopping.domain.Product
import shopping.repository.ProductRepository

@Service
@Transactional
class ProductTransactionalService(
    private val productRepository: ProductRepository,
) {
    fun save(request: ProductRequest): Long = productRepository.save(request.toDomain()).id!!

    @Transactional(readOnly = true)
    fun findById(id: Long): Product =
        productRepository
            .findById(id)
            .orElseThrow { IllegalStateException("상품을 찾을 수 없습니다. id: $id") }

    fun update(
        id: Long,
        request: ProductRequest,
    ) {
        val product = findById(id)
        product.update(request.toDomain())
    }

    fun delete(id: Long) {
        productRepository.deleteById(id)
    }
}
