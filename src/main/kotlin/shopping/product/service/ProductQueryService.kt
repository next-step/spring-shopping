package shopping.product.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.product.entity.Product
import shopping.product.exception.ProductNotFoundException
import shopping.product.repository.ProductRepository

@Transactional(readOnly = true)
@Service
class ProductQueryService(
    private val productRepository: ProductRepository,
) {
    fun findAll(): List<Product> = productRepository.findAll()

    fun getById(id: Long): Product {
        return productRepository.findById(id).orElseThrow { ProductNotFoundException(id) }
    }
}
