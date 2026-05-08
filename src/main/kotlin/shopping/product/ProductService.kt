package shopping.product

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class ProductService {
    private val ids: AtomicLong = AtomicLong(0)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun getProducts(): List<ProductResponse> {
        return products.values.map {
            ProductResponse(it)
        }
    }
    fun getProduct(id: Long): ProductResponse {
        return products[id]?.let {
            ProductResponse(it)
        } ?: throw IllegalArgumentException()
    }
    fun addProduct(request: ProductRequest): ProductResponse {
        val id = ids.incrementAndGet()
        val product = Product(id, request.name, request.price, request.imageUrl)
        products[id] = product
        return ProductResponse(product)
    }
    fun updateProduct(id: Long, request: ProductRequest) {
        val product = products[id] ?: throw IllegalArgumentException()
        product.update(request)
    }
    fun deleteProduct(id: Long) {
        products.remove(id) ?: throw IllegalArgumentException()
    }
}