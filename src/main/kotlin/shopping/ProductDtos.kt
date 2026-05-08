package shopping

import jakarta.validation.constraints.Size
import shopping.domain.Product

data class ProductRequest(
    @field:NoBadWord
    @field:Size(max = 15, message = "상품 이름은 최대 15자까지입니다.")
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