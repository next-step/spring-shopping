package shopping

import org.springframework.stereotype.Service

class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
)

@Service
class ProductService {
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun getProducts(): List<ProductResponse> {
        return products.values.map {
            ProductResponse(it.id, it.name, it.price, it.imageUrl)
        }
    }
}
