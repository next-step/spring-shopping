package shopping.common

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class PurgoMalumVerifier(
    private val restClient: RestClient,
) {
    fun containsProfanity(text: String): Boolean {
        if (text.isBlank()) {
            return false
        }

        val response =
            restClient
                .get()
                .uri("/service/containsprofanity?text=$text")
                .header("Accept", "text/plain")
                .retrieve()
                .body<String>()
        return response?.toBoolean() ?: throw IllegalStateException("Failed to check profanity.")
    }
}
