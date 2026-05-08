package shopping.api

data class ProductRequest(
    val name: String,
    val price: Int,
    val imageUrl: String,
)
