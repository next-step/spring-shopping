package shopping.product.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL
import shopping.product.entity.Product

data class ProductRequestBody(
    @field:Size(
        min = NAME_MIN_LENGTH,
        max = NAME_MAX_LENGTH,
        message = "Name must be between $NAME_MIN_LENGTH and $NAME_MAX_LENGTH characters long",
    )
    @field:Pattern(regexp = NAME_PATTERN, message = "Name contains invalid characters.")
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
    @field:Min(value = MIN_PRICE, message = "Price must be greater than or equal to $MIN_PRICE")
    @field:NotNull(message = "Price must not be null")
    // nullable 유지 (Jackson이 0으로 변환하는 것 방지)
    val price: Int?,
    @field:NotBlank(message = "Image URL must not be blank")
    @field:URL(message = "Image URL must be a valid URL format")
    val imageUrl: String,
) {
    fun toEntity(): Product =
        Product(
            name = name,
            price = requireNotNull(price),
            imageUrl = imageUrl,
        )

    companion object {
        const val NAME_MIN_LENGTH = 1
        const val NAME_MAX_LENGTH = 15
        const val MIN_PRICE = 0L
        const val NAME_PATTERN = "^[a-zA-Z0-9()\\[\\]+\\-&/_ ]+$"
    }
}
