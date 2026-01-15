package shopping.core.product

import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

/**
 * 현재 별도 데이터베이스를 구성하지 않고,
 * 메모리상에서 적절한 코틀린 컬렉션 프레임워크를 이용하여 조회/추가/수정/삭제를 구현한다.
 */
@Repository
class ProductRepository {
    private val storage = mutableMapOf<Long, Product>()
    private var idGenerator = AtomicLong(1L)

    fun findAll(): List<Product> = storage.values.toList()

    fun findById(id: Long): Product? = storage[id]

    fun save(product: Product): Product {
        val id = product.id ?: idGenerator.getAndIncrement()
        val savedProduct = product.copy(id)
        storage[id] = savedProduct
        return savedProduct
    }

    fun deleteById(id: Long): Boolean = storage.remove(id) != null
}
