package shopping.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun getOrThrow(id: Long): Product = findById(id).orElseThrow { IllegalArgumentException("상품을 찾을 수 없습니다: $id") }
}
