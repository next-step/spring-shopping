package shopping.domain

interface ProfanityChecker {
    fun containsProfanity(text: String): Boolean
}
