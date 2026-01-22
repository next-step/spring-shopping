package shopping.controller.model

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import shopping.annotation.NotContainsProfanity
import shopping.repository.model.Product

data class ProductRequestBody(
    @field:Size(min = 1, max = 15)
    @field:Pattern(regexp = "^[a-zA-Z0-9()\\[\\]+\\-&/_ ]+$", message = "Name contains invalid characters.")
    @field:NotContainsProfanity
    val name: String,
    @field:Min(0)
    @field:NotNull
    val price: Int?,
    @field:NotBlank
    val imageUrl: String,
) {
    fun toEntity(): Product =
        Product(
            name = this.name,
            price = this.price!!,
            imageUrl = this.imageUrl,
        )
}
