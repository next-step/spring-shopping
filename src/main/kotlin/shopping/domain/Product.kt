package shopping.domain

import shopping.util.BadWordFilterUtils.checkBadWord

private val PATTERN = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_]*$".toRegex()

class Product(var name: String, var price: Long, var imageUrl: String) {
    init {
        require(name.isNotBlank()) { "상품 이름은 공백만 입력할 수 없습니다. 이름 : $name" }
        require(name.length <= 15) { "상품 이름은 공백을 포함한 15자까지 입력할 수 있습니다. 이름 : $name" }
        require(name.matches(PATTERN)) { "상품 이름은 ( ), [ ], +, -, &, /, _ 특수 문자만 가능합니다. 이름 : $name" }
        require(checkBadWord(name)) { "상품 이름은 비속어를 포함할 수 없습니다. 이름 : $name" }

        require(price > 0) { "상품 가격은 0보다 커야합니다. 가격 : $price" }

        require(imageUrl.isNotBlank()) { "이미지 URL은 빈 값일 수 없습니다. 이미지 URL : $imageUrl" }
    }
}
