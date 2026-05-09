package shopping.domain

class Product (
    val name: String,
    val price: Int,
    val imageUrl: String,
    val id: Long,
) {
    init {
        require(name.length <= MAX_NAME_LENGTH) {
            "상품 이름은 최대 ${MAX_NAME_LENGTH}자까지 입력할 수 있습니다."
        }
        require(ALLOWED_NAME_PATTERN.matches(name)) {
            "상품 이름에 허용되지 않은 특수문자가 포함되어 있습니다."
        }
    }

    companion object {
        private const val MAX_NAME_LENGTH = 15
        private val ALLOWED_NAME_PATTERN = Regex("^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]+$")
    }
}