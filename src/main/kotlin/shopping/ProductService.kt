package shopping

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class ProductService(
    private val productNameFactory: ProductNameFactory,
) {
    private val ids: AtomicLong = AtomicLong(0)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun getProducts(): List<ProductResponse> = products.values.map { it.toResponse() }

    fun addProduct(request: ProductRequest): ProductResponse {
        val id = ids.incrementAndGet()
        val product = Product(id, productNameFactory.create(request.name), request.price, request.imageUrl)
        products[id] = product
        return product.toResponse()
    }
}
