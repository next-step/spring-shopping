package shopping.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

private val PATTERN = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_]*$".toRegex()

@Entity
class Product(
    @Column(name = "name", length = 15, nullable = false)
    private var _name: String,
    @Column(name = "price", nullable = false)
    private var _price: Long,
    @Column(name = "image_url", nullable = false)
    private var _imageUrl: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var _id: Long? = null,
) {
    val name: String
        get() = _name
    val price: Long
        get() = _price
    val imageUrl: String
        get() = _imageUrl
    val id: Long?
        get() = _id

    init {
        validate(name, price, imageUrl)
    }

    fun update(product: Product) {
        validate(product.name, product.price, product.imageUrl)
        this._name = product.name
        this._price = product.price
        this._imageUrl = product.imageUrl
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
    }

    private fun validatePrice(price: Long) {
        require(price > 0) { "상품 가격은 0보다 커야합니다. 가격 : $price" }
    }

    private fun validateImage(imageUrl: String) {
        require(imageUrl.isNotBlank()) { "이미지 URL은 빈 값일 수 없습니다. 이미지 URL : $imageUrl" }
    }
}
