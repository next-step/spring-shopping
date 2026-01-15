package shopping.web.product

import shopping.core.product.Product

data class ProductsResponse(val products: List<ProductResponse>) {
    companion object {
        fun fromDomain(products: List<Product>): ProductsResponse {
            val responses = products.map(ProductResponse.Companion::fromDomain).toList()
            return ProductsResponse(responses)
        }
    }
}
