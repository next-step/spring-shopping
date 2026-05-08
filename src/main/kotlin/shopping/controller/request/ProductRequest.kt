package shopping.controller.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import shopping.domain.Product

data class ProductRequest(
    @field:NotBlank
    val name: String,

    @field:Positive
    @field:NotNull
    val price: Int,

    @field:NotBlank
    val imageUrl: String
) {

    fun from(): Product {
        return Product(
            name = name,
            price = price,
            imageUrl = imageUrl
        )
    }

}