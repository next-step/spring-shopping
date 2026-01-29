package shopping.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import shopping.exception.NotFoundException
import shopping.repository.ProductRepository
import shopping.repository.model.Product

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun findAll(): List<Product> = productRepository.findAll()

    fun getById(id: Long): Product = productRepository.findByIdOrNull(id) ?: throw NotFoundException()

    fun create(product: Product): Product = productRepository.save(product)

    fun update(
        id: Long,
        product: Product,
    ): Product {
        val found =
            productRepository.findByIdOrNull(id)
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
