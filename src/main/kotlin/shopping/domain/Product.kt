package shopping.domain

class Product(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String

)

{
    init {
        require(name.length <= 15) { "상품 이름은 최대 15자까지입니다." }
        require(isValidName(name)) { "사용할 수 없는 특수문자가 포함되어 있습니다." }
    }

    private fun isValidName(name: String): Boolean {
    val allowedPattern = Regex("^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]+$")
    return allowedPattern.matches(name)
}
}