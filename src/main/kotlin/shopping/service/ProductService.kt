package shopping.service

import org.springframework.stereotype.Service
import shopping.controller.request.ProductRequest
import shopping.controller.response.ProductResponse
import shopping.domain.Product
import shopping.domain.repository.ProductRepository

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val profanityValidator: ProfanityValidator
) {

    fun addProduct(request: ProductRequest): Long {
        profanityValidator.validateProfainity(request.name)
        return productRepository.save(Product(request.name, request.price, request.imageUrl))
    }

    fun getProducts(): List<ProductResponse> {
        return ProductResponse.fromList(productRepository.findAll())
    }

    fun getProductById(id: Long): ProductResponse {
        return ProductResponse.from(productRepository.findById(id))
    }

    fun updateProduct(id: Long, request: ProductRequest) {
        profanityValidator.validateProfainity(request.name)
        productRepository.update(id, request.from())
    }

    fun deleteProduct(id: Long) = productRepository.delete(id)
}