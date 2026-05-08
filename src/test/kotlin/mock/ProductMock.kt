package mock

import shopping.domain.Product

object ProductMock {
    val DEFAULT_PRODUCT = Product(
        name = "product name",
        price = 10000,
        imageUrl = "https://example.com/photos/200"
    )

    fun createMockProduct(
        name: String = "product name",
        price: Int = 10000,
        imageUrl: String = "https://example.com/photos/200",
        id: Long? = 0L
    ): Product {
        return Product(
            name = name,
            price = price,
            imageUrl = imageUrl,
            id = id
        )
    }
}
