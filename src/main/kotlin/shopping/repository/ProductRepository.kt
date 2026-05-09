package shopping.repository

import org.springframework.data.jpa.repository.JpaRepository
import shopping.domain.Product

interface ProductRepository : JpaRepository<Product, Long>