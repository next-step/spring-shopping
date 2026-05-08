package shopping.infra

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate

class PurgoMalumProfanityCheckerTest {
    private val restTemplate = RestTemplate()
    private lateinit var mockServer: MockRestServiceServer
    private lateinit var checker: PurgoMalumProfanityChecker

    @BeforeEach
    fun setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
        checker = PurgoMalumProfanityChecker(restTemplate)
    }

    @Test
    fun `비속어가 있는 텍스트는 true를 반환한다`() {
        mockServer
            .expect(requestTo(containsString("containsprofanity")))
            .andRespond(withSuccess("true", MediaType.TEXT_PLAIN))

        assertThat(checker.containsProfanity("ass")).isFalse()
    }

    @Test
    fun `비속어가 없는 텍스트는 true를 반환한다`() {
        mockServer
            .expect(requestTo(containsString("containsprofanity")))
            .andRespond(withSuccess("false", MediaType.TEXT_PLAIN))

        assertThat(checker.containsProfanity("goood")).isTrue()
    }
}
