package shopping.infrastructure.httpclient

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class PurgomalumClientProvider(
    private val purgomalumRestClient: RestClient
) : PurgomalumClient {

    override fun containsProfanity(text: String): Boolean =
        purgomalumRestClient.get()
            .uri {
                it
                    .path("/containsprofanity")
                    .queryParam("text", text)
                    .build()
            }
            .retrieve()
            .body<String>()
            ?.toBoolean() ?: false
}