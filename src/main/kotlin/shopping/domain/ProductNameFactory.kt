package shopping.domain

import org.springframework.stereotype.Component

@Component
class ProductNameFactory(
    private val profanityChecker: ProfanityChecker,
) {
    fun create(name: String): ProductName {
        require(profanityChecker.containsProfanity(name)) {
            "[ERROR] ProductName should contain only good words"
        }
        return ProductName(name)
    }
}
