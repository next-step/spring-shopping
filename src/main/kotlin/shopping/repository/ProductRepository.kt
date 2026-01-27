package shopping.repository

import org.springframework.stereotype.Repository
import shopping.domain.Product
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class ProductRepository {
    private var autoIncrementer: AtomicLong = AtomicLong(0L)
    private val products = ConcurrentHashMap<Long, Product>()

    fun save(product: Product): Long = autoIncrementer.addAndGet(1L).also { products[it] = product }

    fun getById(id: Long) = products[id] ?: throw IllegalArgumentException("상품이 존재하지 않습니다.")

    fun update(
        id: Long,
        product: Product,
    ) {
        getById(id).let {
            if (it.isChanged(product)) {
                it.update(product.name, product.price, product.imageUrl)
            }
        }
    }

    fun delete(id: Long) = products.remove(id)
}
