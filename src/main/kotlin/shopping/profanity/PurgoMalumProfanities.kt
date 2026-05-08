package shopping.profanity

import org.springframework.stereotype.Service

@Service
class PurgoMalumProfanities : Profanities {
    override fun contains(text: String): Boolean = false
}
