package shopping.product

import kotlinx.atomicfu.atomic


class ProductRepository {
    private val idPool = atomic(0)
    private val products: Map<Long, Product> = mutableMapOf()
    fun create(
        name: String,
        price: Int,
        imageUrl: String
    ): Product {
        val product = Product(
            id = idPool.incrementAndGet(),
            name = name,
            price = price,
            imageUrl = imageUrl
        )
        products.plus(product.id to products)
        return product
    }
}