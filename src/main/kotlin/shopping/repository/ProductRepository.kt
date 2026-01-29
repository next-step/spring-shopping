package shopping.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import shopping.domain.Product

@Transactional
interface ProductRepository : JpaRepository<Product, Long>
