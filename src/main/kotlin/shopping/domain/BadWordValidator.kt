package shopping.domain

fun interface BadWordValidator {
    fun containsBadWord(text: String): Boolean
}
