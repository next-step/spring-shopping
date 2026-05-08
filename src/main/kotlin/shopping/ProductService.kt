package shopping

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
)

@Service
class ProductService {
    private val ids: AtomicLong = AtomicLong(0)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun getProducts(): List<ProductResponse> {
        return products.values.map {
            ProductResponse(it.id, it.name, it.price, it.imageUrl)
        }
    }

    fun addProduct(request: ProductRequest): ProductResponse {
        val id = ids.incrementAndGet()
        val product = Product(id, request.name, request.price, request.imageUrl)
        products[id] = product
        return ProductResponse(product.id, product.name, product.price, product.imageUrl)
    }
}
