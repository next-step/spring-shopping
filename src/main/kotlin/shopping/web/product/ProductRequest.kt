package shopping.web.product

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import shopping.core.product.Product

data class ProductRequest(
    @field:NotBlank(message = "이름은 필수입니다.")
    @field:Size(max = 15, message = "이름은 15자리까지 입력할 수 있습니다.")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]+$",
        message = "입력할 수 있는 특수문자는 ( ), [ ], +, -, &, /, _ 입니다.",
    )
    val name: String,
    @field:Min(value = 0, message = "금액은 0 이상이어야 합니다.")
    val price: Long,
    @field:NotBlank(message = "이미지 경로는 필수입니다.")
    val imageUrl: String,
) {
    fun toDomain(): Product {
        return Product(name, price, imageUrl)
    }

    fun toDomain(id: Long): Product {
        return Product(name, price, imageUrl, id)
    }
}
