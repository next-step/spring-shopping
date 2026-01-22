package shopping.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import shopping.annotation.NotContainsProfanity
import shopping.service.PurgoMalumService

@Component
class NotContainsProfanityValidator(private val purgoMalumService: PurgoMalumService) :
    ConstraintValidator<NotContainsProfanity, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?) =
        !purgoMalumService.containsProfanity(value ?: "")
}