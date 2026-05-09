package shopping.dto

import shopping.domain.Product

object ProductFixture {
    fun of(name: String): Product = of(name, 1000)
    fun of(name: String, price: Int): Product = Product(name, price, "https://image.url", 1)
}