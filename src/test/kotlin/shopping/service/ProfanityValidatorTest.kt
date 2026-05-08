package shopping.service

import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import shopping.infrastructure.httpclient.PurgomalumClientProvider

class ProfanityValidatorTest {

    private val profanityValidator: PurgomalumClientProvider = mock(PurgomalumClientProvider::class.java)

    @Test
    fun `상품명에 비속어가 포함되면 에러가 발생한다`() {
        var text = "profinity"
        given(profanityValidator.containsProfanity(text)).willReturn(true)

        shouldThrow<IllegalArgumentException> {
            ProfanityValidator(profanityValidator).validateProfainity(
                text
            )
        }
    }
}