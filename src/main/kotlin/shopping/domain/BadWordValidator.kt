package shopping.domain

fun interface BadWordValidator {
    fun notContainsBadWord(text: String): Boolean
}
