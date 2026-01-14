package shopping.repository

import org.springframework.stereotype.Repository
import shopping.domain.Product
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicLong

@Repository
class ProductRepository {
    private var autoIncrementer: AtomicLong = AtomicLong(0L)
    private val products: MutableMap<Long, Product> = mutableMapOf()

    fun save(product: Product) = products.put(autoIncrementer.addAndGet(1L), product)

    fun get(id: Long) = products[id]

    fun update(
        id: Long,
        product: Product,
    ) {
        val old = products[id] ?: throw IllegalArgumentException("수정할 상품이 없습니다.")

        if (old.isChanged(product)) {
            products.put(id, product)
        }
    }

    fun delete(id: Long) = products.remove(id)
}
