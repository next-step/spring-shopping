package shopping.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopping.exception.NotFoundException
import shopping.repository.ProductRepository
import shopping.repository.model.Product

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
                ?: throw NotFoundException()

        found.apply {
            name = product.name
            price = product.price
            imageUrl = product.imageUrl
        }

        return productRepository.save(found)
    }

    fun deleteById(id: Long) {
        productRepository.deleteById(id)
    }
}
