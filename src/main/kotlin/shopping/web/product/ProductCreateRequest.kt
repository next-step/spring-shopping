package shopping.web.product

import shopping.core.product.Product

data class ProductCreateRequest(val name: String, val price: Long, val imageUrl: String) {
    fun toDomain(): Product {
        return Product(name, price, imageUrl)
    }
}
