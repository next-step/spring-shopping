package shopping.application.product.validator

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ProductValidator(
    val restClient: RestClient,
) {
    fun containsProfanity(text: String): Boolean {
        val response =
            restClient.get()
                .uri("/service/containsprofanity?text={text}", text)
                .retrieve()
                .body(String::class.java)

        return response?.toBoolean() ?: false
    }
}
