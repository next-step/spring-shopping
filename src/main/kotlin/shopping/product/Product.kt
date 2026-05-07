package shopping.product

import jakarta.validation.Valid
import jakarta.validation.constraints.Email


class Product(
    var id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String
) {
    constructor(
        name: String,
        price: Int,
        @Valid @Email imageUrl: String
    ) : this(0, name, price, imageUrl)

    init {
        require(name.length <= 15)
    }
}