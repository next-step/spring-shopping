package shopping.client.purgomalum

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class PurgomalumClient(
    @Qualifier("purgomalumRestClient") private val restClient: RestClient,
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
