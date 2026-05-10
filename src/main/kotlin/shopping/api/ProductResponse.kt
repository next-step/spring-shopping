package shopping.api

import shopping.domain.Product

data class ProductResponse(
    val id: Long,
    var name: String,
    var price: Int,
    val imageUrl: String,
)

fun Product.toResponse() = ProductResponse(id, name.name, price, imageUrl)
