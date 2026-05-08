package shopping.domain

import shopping.api.ProductResponse

class Product(
    val id: Long,
    val name: ProductName,
    val price: Int,
    val imageUrl: String,
) {
    init {
        require(imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
            "[ERROR] imageUrl must start with http:// or https://"
        }
    }

    fun toResponse() = ProductResponse(id, name.name, price, imageUrl)
}
