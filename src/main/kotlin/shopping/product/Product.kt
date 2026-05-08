package shopping.product

private val NAME_PATTERN = Regex("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ ()\\[\\]+\\-&/_]*")
private val URL_PATTERN = Regex("https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+")

class Product(
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    init {
        require(name.length <= 15) {
            "이름은 15자 이내로 입력하십시오."
        }
        require(name.matches(NAME_PATTERN)) {
            "허용되지 않는 특수 문자가 포함되어 있습니다"
        }
        require(imageUrl.matches(URL_PATTERN)) {
            "올바른 URL 형식이 아닙니다"
        }
    }
}
