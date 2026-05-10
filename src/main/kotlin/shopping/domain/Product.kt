package shopping.domain

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
}
