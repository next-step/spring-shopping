package shopping.client

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class BadWordClientTest : FreeSpec({
    "check BadWord" - {
        BadWordClient.checkBadWord("arse") shouldBe false
    }

    "check GoodWord" - {
        BadWordClient.checkBadWord("apple") shouldBe true
    }
})
