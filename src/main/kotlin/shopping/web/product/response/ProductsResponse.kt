package shopping.web.product.response

import shopping.core.product.Product

data class ProductsResponse(val products: List<ProductResponse>) {
    companion object {
        fun fromDomain(products: List<Product>): ProductsResponse {
            return ProductsResponse(products.map(ProductResponse.Companion::fromDomain))
        }
    }
}
