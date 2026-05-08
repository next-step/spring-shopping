package shopping.controller.response

import shopping.domain.Product

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String
) {
    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                id = product.id ?: 0L,
                name = product.name,
                price = product.price,
                imageUrl = product.imageUrl
            )
        }

        fun fromList(products: List<Product>): List<ProductResponse> {
            return products.map { from(it) }
        }
    }
}