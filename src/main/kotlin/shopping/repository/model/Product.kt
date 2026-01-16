package shopping.repository.model

data class Product(
    var name: String,
    var price: Int,
    var imageUrl: String,
    var id: Long? = null,
)
