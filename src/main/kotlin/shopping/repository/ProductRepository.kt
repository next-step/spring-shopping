package shopping.repository

import org.springframework.stereotype.Repository
import shopping.domain.Product
import java.util.concurrent.atomic.AtomicLong

@Repository
class ProductRepository {
    private var autoIncrementer: AtomicLong = AtomicLong(0L)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun save(product: Product): Long {
        return autoIncrementer.addAndGet(1L).also { products[it] = product }
    }

    fun get(id: Long) = products[id] ?: throw IllegalArgumentException("상품이 존재하지 않습니다.")

    fun update(
        id: Long,
        product: Product,
    ) {
        get(id).let {
            if (it.isChanged(product)) {
                products[id] = product
            }
        }
    }

    fun delete(id: Long) = products.remove(id)
}
