package shopping.domain

import shopping.client.BadWordClient.checkBadWord

private val PATTERN = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_]*$".toRegex()

class Product(
    private var _name: String,
    private var _price: Long,
    private var _imageUrl: String,
) {
    val name: String
        get() = _name
    val price: Long
        get() = _price
    val imageUrl: String
        get() = _imageUrl

    init {
        validate(name, price, imageUrl)
    }

    fun isChanged(product: Product): Boolean =
        this._name != product._name || this._price != product._price || this._imageUrl != product._imageUrl

    fun update(
        name: String = this._name,
        price: Long = this._price,
        imageUrl: String = this._imageUrl,
    ) {
        validate(name, price, imageUrl)
        this._name = name
        this._price = price
        this._imageUrl = imageUrl
    }

    private fun validate(
        name: String,
        price: Long,
        imageUrl: String,
    ) {
        validateName(name)
        validatePrice(price)
        validateImage(imageUrl)
    }

    private fun validateName(name: String) {
        require(name.isNotBlank()) { "상품 이름은 공백만 입력할 수 없습니다. 이름 : $name" }
        require(name.length <= 15) { "상품 이름은 공백을 포함한 15자까지 입력할 수 있습니다. 이름 : $name" }
        require(name.matches(PATTERN)) { "상품 이름은 ( ), [ ], +, -, &, /, _ 특수 문자만 가능합니다. 이름 : $name" }
        require(checkBadWord(name)) { "상품 이름은 비속어를 포함할 수 없습니다. 이름 : $name" }
    }

    private fun validatePrice(price: Long) {
        require(price > 0) { "상품 가격은 0보다 커야합니다. 가격 : $price" }
    }

    private fun validateImage(imageUrl: String) {
        require(imageUrl.isNotBlank()) { "이미지 URL은 빈 값일 수 없습니다. 이미지 URL : $imageUrl" }
    }
}
