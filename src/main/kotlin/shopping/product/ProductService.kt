package shopping.product

import org.springframework.stereotype.Service
import shopping.profanity.Profanities

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val profanities: Profanities,
) {
    fun getProduct(id: Long): ProductResponse = ProductResponse.of(productRepository.getOrThrow(id))

    fun addProduct(request: ProductRequest): ProductResponse {
        val product = Product.of(request, profanities)
        productRepository.save(product)
        return ProductResponse.of(product)
    }

    fun updateProduct(
        id: Long,
        request: ProductRequest,
    ) {
        val product = productRepository.getOrThrow(id)
        product.update(request, profanities)
        productRepository.save(product)
    }

    fun deleteProduct(id: Long) {
        val product = productRepository.getOrThrow(id)
        productRepository.delete(product)
    }
}
