package shopping.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import shopping.dto.ProductFixture

open class ProductTest : FreeSpec({
    "상품 이름은 15자를 초과할 수 없다" - {
        withData(
            "a".repeat(16),
            "a".repeat(20),
        ) { name ->
            shouldThrow<IllegalArgumentException> { ProductFixture.of(name) }
        }
    }

    "상품 이름에 허용된 특수문자는 사용 가능하다 - (), [], +, -, &, /, _" - {
        withData(
            "[아메리카노]",
            "에스프레소+카페라떼",
            "아메리카노(tall)",
            "카페_라떼"
        ) { name ->
            shouldNotThrowAny { ProductFixture.of(name) }
        }
    }

    "허용되지 않은 특수문자는 사용 불가능하다" - {
        withData(
            "*아메리카노*",
            "<에스프레소>",
            "카페!라떼"
        ) { name ->
            shouldThrow<IllegalArgumentException> { ProductFixture.of(name) }
        }
    }
})