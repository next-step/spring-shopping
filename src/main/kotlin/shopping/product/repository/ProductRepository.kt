package shopping.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shopping.product.entity.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long>
