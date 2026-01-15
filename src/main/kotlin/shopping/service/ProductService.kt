package shopping.service

import org.springframework.stereotype.Service
import shopping.controller.dto.ProductRequest
import shopping.domain.Product
import shopping.repository.ProductRepository

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun save(request: ProductRequest) = request.run { productRepository.save(Product(name, price, imageUrl)) }

    fun getById(id: Long) = productRepository.getById(id)

    fun update(
        id: Long,
        request: ProductRequest,
    ) = productRepository.update(id, request.run { Product(name, price, imageUrl) })

    fun delete(id: Long) = productRepository.delete(id)
}
