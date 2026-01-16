package shopping.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class PurgoMalumService(
    private val restTemplate: RestTemplate,
) {
    fun containsProfanity(text: String): Boolean {
        if (text.isBlank()) {
            return false
        }

        val url = "http://www.purgomalum.com/service/containsprofanity?text=$text"
        val headers = HttpHeaders().apply { set("Accept", "text/plain") }
        val entity = HttpEntity<String>(headers)
        val result = restTemplate.exchange<String>(url, HttpMethod.GET, entity)
        return result.body?.toBoolean() ?: throw IllegalStateException("Failed to check profanity.")
    }
}
