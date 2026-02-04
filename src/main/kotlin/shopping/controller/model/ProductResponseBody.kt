package shopping.controller.model

import shopping.repository.model.Product

data class ProductResponseBody(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    companion object {
        fun from(product: Product): ProductResponseBody =
            ProductResponseBody(
                id = product.id!!,
                name = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
            )
    }
}
