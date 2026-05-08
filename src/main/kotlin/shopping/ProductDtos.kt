package shopping

import shopping.domain.Product

data class ProductRequest(
    val name: String,
    val price: Int,
    val imageUrl: String)

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String) {
    constructor(product: Product): this(product.id, product.name, product.price, product.imageUrl)
}