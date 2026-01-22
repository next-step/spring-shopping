package shopping.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class PurgoMalumService(
    private val restClient: RestClient
) {
    fun containsProfanity(text: String): Boolean {
        if (text.isBlank()) {
            return false
        }

        val response = restClient.get().uri("/service/containsprofanity?text=$text")
            .header("Accept", "text/plain")
            .retrieve()
            .body(String::class.java);
        return response?.toBoolean() ?: throw IllegalStateException("Failed to check profanity.")
    }
}
