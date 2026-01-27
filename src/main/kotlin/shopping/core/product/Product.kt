package shopping.core.product

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Table(name = "products")
@Entity
@EntityListeners(AuditingEntityListener::class)
class Product(
    @Column(nullable = false, length = 15)
    val name: String = "",

    @Column(nullable = false)
    val price: Long = 0,

    @Column(nullable = false, length = 500)
    val imageUrl: String = "",

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set
}
