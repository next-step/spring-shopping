package shopping.web.product

import shopping.core.product.Product

data class ProductUpdateRequest(val name: String, val price: Long, val imageUrl: String, val id: Long) {
    fun toDomain(): Product {
        return Product(name, price, imageUrl, id)
    }
}
