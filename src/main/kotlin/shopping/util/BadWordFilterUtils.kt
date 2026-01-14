package shopping.util

import com.vane.badwordfiltering.BadWordFiltering

object BadWordFilterUtils {
    val badWordFiltering = BadWordFiltering()

    fun checkBadWord(word: String): Boolean {
        return !badWordFiltering.check(word)
    }
}
