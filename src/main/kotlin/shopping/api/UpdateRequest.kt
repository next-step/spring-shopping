package shopping.api

data class UpdateRequest(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
