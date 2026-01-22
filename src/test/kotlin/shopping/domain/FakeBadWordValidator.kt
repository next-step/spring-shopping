package shopping.domain

class FakeBadWordValidator(
    private val badWords: Set<String> = emptySet(),
) : BadWordValidator {
    override fun containsBadWord(text: String): Boolean = badWords.any { text.contains(it) }
}
