package shopping.repository

import org.springframework.stereotype.Repository
import shopping.repository.model.Product
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class ProductRepository {
    private val idGenerator = AtomicLong(1L)
    private val products = ConcurrentHashMap<Long, Product>()

    fun save(product: Product): Product {
        val id = product.id ?: getNextId()
        product.id = id
        products[id] = product
        return product
    }

    fun findById(id: Long): Product? = products[id]

    fun findAll(): List<Product> = products.values.sortedBy { it.id }.toList()

    fun deleteById(id: Long) {
        products.remove(id)
    }

    fun getNextId(): Long = idGenerator.getAndIncrement()
}
