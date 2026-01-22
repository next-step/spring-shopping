package shopping.annotation

import jakarta.validation.Constraint
import shopping.validator.NotContainsProfanityValidator
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [NotContainsProfanityValidator::class])
annotation class NotContainsProfanity(
    val message: String = "The value contains profanity.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = [],
)
