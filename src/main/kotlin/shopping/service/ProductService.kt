package shopping.service

import org.springframework.stereotype.Service
import shopping.controller.dto.ProductRequest
import shopping.controller.dto.ProductResponse
import shopping.domain.Product
import shopping.repository.ProductRepository

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun save(request: ProductRequest): Long =
        request.run { productRepository.save(Product(name, price, imageUrl)).id!! }

    fun getById(id: Long): ProductResponse =
        productRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("상품이 존재하지 않습니다.") }
            .let { ProductResponse(it.name, it.price, it.imageUrl) }

    fun update(
        id: Long,
        request: ProductRequest,
    ) {
        val product = productRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("상품이 존재하지 않습니다.") }
        product.update(request.name, request.price, request.imageUrl)
        productRepository.save(product)
    }

    fun delete(id: Long): Unit = productRepository.deleteById(id)
}
