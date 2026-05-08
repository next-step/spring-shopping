package shopping.domain.repository

import shopping.domain.Product

interface ProductRepository {

    fun save(product: Product): Long

    fun findAll(): List<Product>

    fun findById(id: Long): Product

    fun update(id: Long, product: Product)

    fun delete(id: Long)
}