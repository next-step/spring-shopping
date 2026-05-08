package shopping.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import shopping.NoBadWord

@Entity
@Table(name = "products")
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String,
    var price: Int,
    var imageUrl: String

)

{
    init {
        require(isValidName(name)) { "사용할 수 없는 특수문자가 포함되어 있습니다." }
        require(name.length <= 15) { "상품이름은 15자 이상은 불가합니다." }
    }

    private fun isValidName(name: String): Boolean {
    val allowedPattern = Regex("^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]+$")
    return allowedPattern.matches(name)
}
}