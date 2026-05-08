package shopping.infrastructure.database

import org.springframework.stereotype.Repository
import shopping.domain.Product
import shopping.domain.repository.ProductRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class ProductRepositoryImpl : ProductRepository {

    private var productIdGenerator: AtomicLong = AtomicLong(0L)
    private val products: ConcurrentHashMap<Long, Product> = ConcurrentHashMap()

    override fun save(product: Product): Long =
        productIdGenerator.addAndGet(1L)
            .also { products[it] = product }

    override fun findAll(): List<Product> {
        return products.entries
            .sortedBy { it.key }
            .map { it.value }
    }

    override fun findById(id: Long): Product {
        return products[id] ?: throw NoSuchElementException("Product doesn't exist")
    }

    override fun update(id: Long, product: Product) {
        findById(id).update(product)
    }

    override fun delete(id: Long) {
        products.remove(id)
    }
}