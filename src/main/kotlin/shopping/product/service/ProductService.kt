package shopping.product.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.product.exception.ProductNotFoundException
import shopping.product.repository.ProductRepository
import shopping.product.entity.Product

@Transactional(readOnly = true)
@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun findAll(): List<Product> = productRepository.findAll()

    fun findById(id: Long): Product? = productRepository.findById(id)

    @Transactional
    fun create(product: Product): Product = productRepository.save(product)

    @Transactional
    fun update(
        id: Long,
        product: Product,
    ): Product {
        val found =
            productRepository.findById(id)
                ?: throw ProductNotFoundException(id)

        found.apply {
            name = product.name
            price = product.price
            imageUrl = product.imageUrl
        }

        return productRepository.save(found)
    }

    @Transactional
    fun deleteById(id: Long) {
        productRepository.deleteById(id)
    }
}
