package shopping.product.dto.response

import shopping.product.entity.Product

data class ProductResponseBody(
    val name: String,
    val price: Int,
    val imageUrl: String,
    val id: Long,
) {
    companion object {
        fun from(product: Product): ProductResponseBody =
            ProductResponseBody(
                name = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
                id = requireNotNull(product.id),
            )
    }
}
