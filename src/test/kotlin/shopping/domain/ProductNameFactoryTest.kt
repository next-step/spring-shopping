package shopping.domain

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ProductNameFactoryTest {
    private val profanityChecker = mockk<ProfanityChecker>()
    private val factory = ProductNameFactory(profanityChecker)

    @Test
    fun `비속어가 포함된 상품명은 생성에 실패한다`() {
        every { profanityChecker.containsProfanity("ass") } returns true

        assertThrows<IllegalArgumentException> {
            factory.create("ass")
        }
    }

    @Test
    fun `PurgoMalum을 통과한 이름만 사용 가능하다`() {
        every { profanityChecker.containsProfanity("bastard1234") } returns true

        assertThrows<IllegalArgumentException> {
            factory.create("bastard1234")
        }
    }

    @Test
    fun `비속어가 없는 상품명은 정상적으로 생성된다`() {
        every { profanityChecker.containsProfanity("monitor") } returns false

        assertDoesNotThrow {
            factory.create("monitor")
        }
    }
}
