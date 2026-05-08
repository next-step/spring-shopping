package shopping

data class ProductResponse(
    val id: Long,
    var name: String,
    var price: Int,
    val imageUrl: String,
)
