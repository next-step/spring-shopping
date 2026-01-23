package shopping.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ProductTest :
    StringSpec({
        "상품 생성 성공" {
            val product = Product("좋은상품", 1000, "http://image.url")
            product.name shouldBe "좋은상품"
            product.price shouldBe 1000
        }

        "상품 이름 15자 초과시 예외 발생" {
            shouldThrow<IllegalArgumentException> {
                Product("가".repeat(16), 1000, "http://image.url")
            }
        }

        "가격이 0 이하일 때 예외 발생" {
            shouldThrow<IllegalArgumentException> {
                Product("상품", 0, "http://image.url")
            }
        }

        "이미지 URL이 빈 값일 때 예외 발생" {
            shouldThrow<IllegalArgumentException> {
                Product("상품", 1000, "")
            }
        }
    })
