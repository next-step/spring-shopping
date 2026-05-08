package shopping.domain

import shopping.api.ProductResponse

class Product(
    val id: Long,
    val name: ProductName,
    val price: Int,
    val imageUrl: String,
) {
    fun toResponse() = ProductResponse(id, name.name, price, imageUrl)
}
