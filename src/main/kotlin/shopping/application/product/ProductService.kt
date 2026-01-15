package shopping.application.product

import org.springframework.stereotype.Service
import shopping.core.product.Product
import shopping.core.product.ProductRepository
import shopping.web.product.ProductResponse
import shopping.web.product.ProductsResponse

@Service
class ProductService(val productRepository: ProductRepository) {
    fun findAll(): ProductsResponse = ProductsResponse.fromDomain(productRepository.findAll())

    fun findById(id: Long): ProductResponse {
        val product = productRepository.findById(id) ?: throw IllegalArgumentException("Product not found")
        return ProductResponse.fromDomain(product)
    }

    fun save(product: Product): ProductResponse = ProductResponse.fromDomain(productRepository.save(product))

    fun deleteById(id: Long): Boolean = productRepository.deleteById(id)
}
