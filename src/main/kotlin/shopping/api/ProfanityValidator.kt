package shopping.api

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import shopping.dto.NoBadWord

@Component
class ProfanityValidator(
    private val restClient: RestClient
) : ConstraintValidator<NoBadWord, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        return runCatching {
            val result = restClient.get()
                .uri("$PROFANITY_API_URL$value")
                .retrieve()
                .body(String::class.java)
            result == "false"
        }.getOrDefault(true)
    }

    companion object {
        private const val PROFANITY_API_URL =
            "https://www.purgomalum.com/service/containsprofanity?text="
    }
}