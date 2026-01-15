package shopping.client

import org.springframework.web.client.RestClient
import org.springframework.web.client.body

object BadWordClient {
    val client = RestClient.builder()
        .baseUrl("http://www.purgomalum.com/service")
        .build()

    fun checkBadWord(word: String): Boolean {
        return !client.get()
            .uri {
                it.path("/containsprofanity")
                    .queryParam("text", word)
                    .build()
            }
            .retrieve()
            .body<String>()
            .toBoolean()
    }
}
