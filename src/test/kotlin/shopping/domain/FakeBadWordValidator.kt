package shopping.domain

class FakeBadWordValidator(
    private val badWords: Set<String> = emptySet(),
) : BadWordValidator {
    override fun notContainsBadWord(text: String): Boolean = badWords.none { text.contains(it) }
}
