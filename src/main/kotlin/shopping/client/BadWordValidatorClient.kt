package shopping.client

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import shopping.domain.BadWordValidator

@Component
class BadWordValidatorClient : BadWordValidator {
    private val client =
        RestClient
            .builder()
            .baseUrl("http://www.purgomalum.com/service")
            .build()

    override fun containsBadWord(text: String): Boolean =
        !client
            .get()
            .uri {
                it
                    .path("/containsprofanity")
                    .queryParam("text", text)
                    .build()
            }.retrieve()
            .body<String>()
            .toBoolean()
}
