package shopping

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class PurgoMalumProfanityChecker(
    private val restTemplate: RestTemplate,
) : ProfanityChecker {
    override fun containsProfanity(text: String): Boolean {
        val url = "https://www.purgomalum.com/service/containsprofanity?text=$text"
        val response = restTemplate.getForObject(url, String::class.java)
        return response?.trim() != "true"
    }
}
