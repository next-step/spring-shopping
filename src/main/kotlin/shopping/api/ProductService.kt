package shopping.api

import org.springframework.stereotype.Service
import shopping.domain.Product
import shopping.domain.ProductNameFactory
import shopping.infra.ProductRepository

@Service
class ProductService(
    private val productNameFactory: ProductNameFactory,
    private val productRepository: ProductRepository,
) {
    fun getProducts(): List<ProductResponse> = productRepository.findAll().map { it.toResponse() }

    fun addProduct(request: ProductRequest): ProductResponse {
        val product = Product(0, productNameFactory.create(request.name), request.price, request.imageUrl)
        return productRepository.save(product).toResponse()
    }

    fun updateProduct(request: UpdateRequest): ProductResponse {
        getSingleProduct(request.id)
        val updated =
            Product(
                id = request.id,
                name = productNameFactory.create(request.name),
                price = request.price,
                imageUrl = request.imageUrl,
            )
        productRepository.update(updated)
        return updated.toResponse()
    }

    fun getSingleProduct(id: Long): ProductResponse {
        return productRepository.findById(id)?.toResponse() ?: throw ProductNotFoundException(id)
    }

    fun deleteProduct(id: Long) {
        productRepository.delete(id)
    }
}
