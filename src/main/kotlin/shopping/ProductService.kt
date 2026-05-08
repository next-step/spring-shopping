package shopping

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import shopping.config.ProductRepository
import shopping.domain.Product
import java.util.concurrent.atomic.AtomicLong

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository
) {
    fun genProducts(): List<ProductResponse> {
            return productRepository.findAll().map(::ProductResponse)
    }

    fun addProduct(request: ProductRequest): ProductResponse {
            return Product(name = request.name, price = request.price, imageUrl = request.imageUrl)
                .let { productRepository.save(it) }
                .let(::ProductResponse)
    }

    fun update(id: Long, request: ProductRequest): ProductResponse {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("상품이 존재하지 않습니다. id: $id") }
        product.name = request.name
        product.price = request.price
        product.imageUrl = request.imageUrl
        return ProductResponse(product)
    }

    fun delete(id: Long) {
        productRepository.deleteById(id)
    }
}
