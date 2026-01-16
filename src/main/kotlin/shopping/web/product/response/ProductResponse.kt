package shopping.web.product.response

import shopping.core.product.Product

data class ProductResponse(val name: String, val price: Long, val imageUrl: String, val id: Long) {
    companion object {
        fun fromDomain(product: Product): ProductResponse {
            return ProductResponse(
                name = product.name,
                price = product.price,
                imageUrl = product.imageUrl,
                id = product.id,
            )
        }
    }
}
