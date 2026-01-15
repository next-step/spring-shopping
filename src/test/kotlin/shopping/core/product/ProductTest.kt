package shopping.core.product

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ProductTest : FreeSpec({
    "Product 초기화 테스트" - {
        "초기화 시 id null 테스트" {
            val product = Product("name", 10000L, "image")
            product.id shouldBe null
        }
    }
})
