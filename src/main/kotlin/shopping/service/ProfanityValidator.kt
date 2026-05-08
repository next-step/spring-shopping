package shopping.service

import org.springframework.stereotype.Component
import shopping.infrastructure.httpclient.PurgomalumClientProvider

@Component
class ProfanityValidator(
    private val profanityValidator: PurgomalumClientProvider
) {

    fun validateProfainity(text: String) =
        require(  !profanityValidator.containsProfanity(text)) {
            "Profanity text '$text' can't contain."
        }
}