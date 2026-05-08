package shopping

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String
)

@Service
class ProductService {
    private val ids: AtomicLong = AtomicLong(0)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun genProducts(): List<ProductResponse> {
        return products.values.map(::ProductResponse)
    }

    fun addProduct(request: ProductRequest): ProductResponse {
        val id = ids.incrementAndGet()
        return Product(id, request.name, request.price, request.imageUrl)
            .also { products[id] = it }
            .let(::ProductResponse)
    }
}
