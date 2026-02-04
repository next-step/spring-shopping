package shopping.application.product.validator

import org.springframework.stereotype.Component
import shopping.client.purgomalum.PurgomalumClient

@Component
class ProductValidator(
    private val purgomalumClient: PurgomalumClient,
) {
    fun hasNoProfanity(text: String): Boolean {
        return !purgomalumClient.containsProfanity(text)
    }
}
