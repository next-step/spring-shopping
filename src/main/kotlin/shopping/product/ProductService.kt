package shopping.product

import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun getProduct(id: Long): ProductResponse = ProductResponse.of(productRepository.getOrThrow(id))

    fun addProduct(request: ProductRequest): ProductResponse {
        val product = productRepository.save(request)
        return ProductResponse.of(product)
    }

    fun updateProduct(
        id: Long,
        request: ProductRequest,
    ) {
        productRepository.update(id, request)
    }

    fun deleteProduct(id: Long) {
        productRepository.delete(id)
    }
}
