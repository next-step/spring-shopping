package shopping.controller.model

import shopping.repository.model.Product

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    companion object {
        fun from(product: Product): ProductResponse =
            with(product) {
                ProductResponse(
                    id = id!!,
                    name = name,
                    price = price,
                    imageUrl = imageUrl,
                )
            }
    }
}
