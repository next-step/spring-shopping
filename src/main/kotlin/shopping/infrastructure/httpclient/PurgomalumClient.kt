package shopping.infrastructure.httpclient

fun interface PurgomalumClient {
    fun containsProfanity(text: String): Boolean
}