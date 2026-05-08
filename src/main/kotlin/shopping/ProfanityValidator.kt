package shopping

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

// ProfanityValidator.kt
@Component
class ProfanityValidator(
    private val restClient: RestClient
) : ConstraintValidator<NoBadWord, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        val result = restClient.get()
            .uri("https://www.purgomalum.com/service/containsprofanity?text=$value")
            .retrieve()
            .body(String::class.java)
        return result == "false"
    }
}