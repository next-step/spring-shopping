package shopping.domain

import jakarta.validation.constraints.Size
import shopping.NoBadWord

class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String

)

{
    init {
        require(isValidName(name)) { "사용할 수 없는 특수문자가 포함되어 있습니다." }
    }

    private fun isValidName(name: String): Boolean {
    val allowedPattern = Regex("^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]+$")
    return allowedPattern.matches(name)
}
}