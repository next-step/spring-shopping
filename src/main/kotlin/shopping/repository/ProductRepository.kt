package shopping.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import shopping.repository.model.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long>
