package shopping.product.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.product.entity.Product
import shopping.product.repository.ProductRepository

@Transactional
@Service
class ProductCommandService(
    private val productQueryService: ProductQueryService,
    private val productRepository: ProductRepository,
) {
    fun create(product: Product): Product = productRepository.save(product)

    fun update(
        id: Long,
        product: Product,
    ): Product {
        val found = productQueryService.getById(id)

        found.apply {
            name = product.name
            price = product.price
            imageUrl = product.imageUrl
        }

        return productRepository.save(found)
    }

    fun deleteById(id: Long) {
        val found = productQueryService.getById(id)
        productRepository.delete(found)
    }
}
