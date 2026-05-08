package shopping.product

import kotlinx.atomicfu.atomic
import org.springframework.stereotype.Repository
import shopping.profanity.Profanities

@Repository
class ProductRepository(
    private val profanities: Profanities,
) {
    private val ids = atomic(0L)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun getOrThrow(id: Long): Product = products[id] ?: throw IllegalArgumentException()

    fun save(request: ProductRequest): Product {
        val id = ids.incrementAndGet()
        val product = Product.of(id, request, profanities)
        products[id] = product
        return product
    }

    fun update(
        id: Long,
        request: ProductRequest,
    ) {
        val product = getOrThrow(id)
        product.update(request)
    }

    fun delete(id: Long) {
        getOrThrow(id)
        products.remove(id)
    }
}
