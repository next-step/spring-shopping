package shopping.dto

import jakarta.validation.Constraint
import jakarta.validation.Payload
import shopping.api.ProfanityValidator
import shopping.domain.Product
import kotlin.reflect.KClass

data class ProductRequest(
    @field:NoBadWord
    val name: String,
    val price: Int,
    val imageUrl: String
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String
) {
    constructor(product: Product) : this(
        id = product.id,
        name = product.name,
        price = product.price,
        imageUrl = product.imageUrl
    )
}

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ProfanityValidator::class])
annotation class NoBadWord(
    val message: String = "비속어가 포함되어 있습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)