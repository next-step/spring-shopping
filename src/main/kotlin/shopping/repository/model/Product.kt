package shopping.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "product")
class Product(
    @Column(name = "name", nullable = false, length = 15)
    var name: String,
    @Column(name = "price", nullable = false)
    var price: Int,
    @Column(name = "image_url", nullable = false)
    var imageUrl: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,
)
