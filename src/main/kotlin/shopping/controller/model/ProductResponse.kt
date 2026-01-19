package shopping.controller.model

import shopping.repository.model.Product

data class ProductResponse(
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    companion object {
        fun from(product: Product): ProductResponse =
            with(product) {
                ProductResponse(
                    name = name,
                    price = price,
                    imageUrl = imageUrl,
                )
            }
    }
}
