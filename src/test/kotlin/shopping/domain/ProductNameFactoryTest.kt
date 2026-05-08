package shopping.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.web.client.RestTemplate
import shopping.infra.PurgoMalumProfanityChecker

class ProductNameFactoryTest {
    private val restTemplate = RestTemplate()
    private val profanityChecker = PurgoMalumProfanityChecker(restTemplate)
    private val factory = ProductNameFactory(profanityChecker)

    @Test
    fun `비속어가 포함된 상품명은 생성에 실패한다`() {
        assertThrows<IllegalArgumentException> {
            factory.create("ass")
        }
    }

    @Test
    fun `PurgoMalum을 통과한 이름만 사용 가능하다`() {
        assertThrows<IllegalArgumentException> {
            factory.create("bastard1234")
        }
    }

    @Test
    fun `비속어가 없는 상품명은 정상적으로 생성된다`() {
        assertDoesNotThrow {
            factory.create("monitor")
        }
    }
}
