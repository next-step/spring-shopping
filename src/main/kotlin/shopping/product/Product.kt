package shopping.product

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Transient
import shopping.profanity.Profanities

private val NAME_PATTERN = Regex("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ ()\\[\\]+\\-&/_]*")
private val URL_PATTERN = Regex("https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+")

@Entity
class Product(
    var name: String,
    var price: Int,
    var imageUrl: String,
    @Transient
    val profanities: Profanities,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
) {
    fun update(
        request: ProductRequest,
        profanities: Profanities,
    ) {
        name = request.name
        price = request.price
        imageUrl = request.imageUrl

        checkConstraints(profanities)
    }

    init {
        checkConstraints(profanities)
    }

    private fun checkConstraints(profanities: Profanities) {
        require(name.length <= 15) {
            "이름은 15자 이내로 입력하십시오."
        }
        require(name.matches(NAME_PATTERN)) {
            "이름에 허용되지 않는 특수 문자가 포함되어 있습니다"
        }
        require(name !in profanities) {
            "이름에 허용되지 않는 비속어가 포함되어 있습니다."
        }
        require(imageUrl.matches(URL_PATTERN)) {
            "이미지URL이 올바른 URL 형식이 아닙니다"
        }
    }

    companion object {
        fun of(
            request: ProductRequest,
            profanities: Profanities,
        ): Product =
            Product(
                name = request.name,
                price = request.price,
                imageUrl = request.imageUrl,
                profanities = profanities,
            )
    }
}
