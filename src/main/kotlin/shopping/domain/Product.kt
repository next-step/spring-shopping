package shopping.domain

class Product(
    name: String,
    price: Int,
    imageUrl: String,
    val id: Long? = 0L
) {
    private val PATTERN_REGEX = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_\\s]*$".toRegex()

    var name = name
        private set

    var price = price
        private set

    var imageUrl = imageUrl
        private set

    init {
        validateProduct(name, price, imageUrl)
    }

    fun update(product: Product) {
        if (isChanged(product)) {
            update(product.name, product.price, product.imageUrl)
        }
    }

    private fun validateProduct(name: String, price: Int, imageUrl: String) {
        validateName(name)
        validatePrice(price)
        validateImageUrl(imageUrl)
    }

    private fun validateImageUrl(imageUrl: String) {
        require(imageUrl.isNotBlank()) { "Image URL must not be blank" }
    }

    private fun validatePrice(price: Int) {
        require(price > 0) { "Price must be greater than zero. price: $price" }
    }

    private fun validateName(name: String) {
        require(name.isNotBlank()) { "Product name must not be blank" }
        require(name.length <= 15) { "Product name can be up to 15 characters including spaces. Product Name : $name" }
        require(name.matches(PATTERN_REGEX)) { "Product name can only contain alphanumeric characters and '( ) [ ] + - & / _' Product Name: $name" }
    }

    private fun isChanged(product: Product): Boolean =
        this.name != product.name
                || this.price != product.price
                || this.imageUrl != product.imageUrl

    private fun update(
        name: String,
        price: Int,
        imageUrl: String
    ) {
        validateProduct(name, price, imageUrl)
        this.name = name
        this.price = price
        this.imageUrl = imageUrl
    }
}