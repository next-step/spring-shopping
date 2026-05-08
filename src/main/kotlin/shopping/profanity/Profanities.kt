package shopping.profanity

fun interface Profanities {
    operator fun contains(text: String): Boolean
}