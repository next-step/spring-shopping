package shopping.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class ProductRequest(
    @field:NotBlank
    val name: String,
    @field:Positive
    val price: Long,
    @field:NotBlank
    val imageUrl: String,
)
