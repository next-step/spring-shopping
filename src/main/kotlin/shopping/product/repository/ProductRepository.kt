package shopping.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import shopping.product.entity.Product

interface ProductRepository : JpaRepository<Product, Long>