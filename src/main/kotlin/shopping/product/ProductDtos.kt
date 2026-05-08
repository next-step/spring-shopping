package shopping.product

data class ProductRequest(
    val name: String,
    val price: Int,
    val imageUrl: String,
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    companion object {
        fun of(product: Product): ProductResponse =
            ProductResponse(
                id = product.id,
                name = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
            )
    }
}
