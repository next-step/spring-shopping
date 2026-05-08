package shopping.profanity

import org.springframework.stereotype.Service

/**
 * TODO 외부 호출 구현
 */
@Service
class FakeProfanities : Profanities {
    override fun contains(text: String): Boolean = false
}
