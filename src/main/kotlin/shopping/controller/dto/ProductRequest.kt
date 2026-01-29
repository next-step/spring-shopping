package shopping.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import shopping.domain.Product

data class ProductRequest(
    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,
    @field:Positive
    @field:NotNull
    @JsonProperty("price")
    private val _price: Long?,
    @field:NotBlank
    @JsonProperty("imageUrl")
    private val _imageUrl: String?,
) {
    val name: String get() = _name!!
    val price: Long get() = _price!!
    val imageUrl: String get() = _imageUrl!!

    fun toDomain(): Product = Product(name, price, imageUrl)
}
