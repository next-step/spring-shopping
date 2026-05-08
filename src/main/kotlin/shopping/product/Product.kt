package shopping.product

import jakarta.validation.Valid
import jakarta.validation.constraints.Email


class Product(
    var id: Long,
    var name: String,
    var price: Int,
    var imageUrl: String
) {
    fun update(request: ProductRequest) {
        name = request.name
        price = request.price
        imageUrl = request.imageUrl
    }

    constructor(
        name: String,
        price: Int,
        @Valid @Email imageUrl: String
    ) : this(0, name, price, imageUrl)

    init {
        require(name.length <= 15)
    }
}