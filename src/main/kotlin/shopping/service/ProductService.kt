package shopping.service

import shopping.domain.Product
import shopping.dto.ProductRequest
import java.util.concurrent.atomic.AtomicLong

class ProductService(
    private val repository: MutableMap<Long, Product>) {
    private val idGenerator = AtomicLong(0)

    fun getProducts(): List<Product> = repository.values.toList()

    fun getProduct(id: Long): Product = findProduct(id)

    fun addProduct(request: ProductRequest): Product {
        val id = idGenerator.incrementAndGet()

        return Product(request.name, request.price, request.imageUrl, id)
            .also { repository[id] = it }
    }

    fun update(id: Long, request: ProductRequest): Product {
        findProduct(id)

        return Product(request.name, request.price, request.imageUrl, id)
            .also { repository[id] = it }
    }

    fun delete(id: Long) {
        findProduct(id)
        repository.remove(id)
    }

    private fun findProduct(id: Long): Product =
        repository[id] ?: throw IllegalArgumentException("상품(id=$id)이 존재하지 않습니다.")
}