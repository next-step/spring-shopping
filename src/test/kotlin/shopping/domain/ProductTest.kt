package shopping.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ProductTest : FreeSpec({

    "상품의 이름을 검증한다" - {
        "상품의 이름은 공백만 있을 수 없다" - {
            val name = "    "
            val exception = shouldThrow<IllegalArgumentException> { Product(name, 100L, "url") }

            exception.message shouldBe "상품 이름은 공백만 입력할 수 없습니다. 이름 : $name"
        }

        "상품의 이름은 15자 이상일 수 없다." - {
            val name = "1234567891234567"
            val exception = shouldThrow<IllegalArgumentException> { Product(name, 100L, "url") }

            exception.message shouldBe "상품 이름은 공백을 포함한 15자까지 입력할 수 있습니다. 이름 : $name"
        }

        "상품의 이름은 ( ), [ ], +, -, &, /, _ 특수문자만 가능하다." - {
            val name = "ㅁㄴㅇㄹ%"
            val exception = shouldThrow<IllegalArgumentException> { Product(name, 100L, "url") }

            exception.message shouldBe "상품 이름은 ( ), [ ], +, -, &, /, _ 특수 문자만 가능합니다. 이름 : $name"
        }

        "상품의 이름은 비속어가 불가하다." - {
            val name = "arse"
            val exception = shouldThrow<IllegalArgumentException> { Product(name, 100L, "url") }

            exception.message shouldBe "상품 이름은 비속어를 포함할 수 없습니다. 이름 : $name"
        }
    }

    "상품의 가격을 검증한다" - {
        "상품의 가격은 0보다 커야한다." - {
            val price = 0L
            val exception = shouldThrow<IllegalArgumentException> { Product("name", price, "url") }

            exception.message shouldBe "상품 가격은 0보다 커야합니다. 가격 : $price"
        }
    }

    "상품의 이미지 URL을 검증한다" - {
        "이미지 URL은 빈 값일 수 없습니다." - {
            val imageUrl = ""
            val exception = shouldThrow<IllegalArgumentException> { Product("name", 1000L, imageUrl) }

            exception.message shouldBe "이미지 URL은 빈 값일 수 없습니다. 이미지 URL : $imageUrl"
        }
    }

    "상품 변경 확인 테스트" - {
        "상품의 이름이 변경된 경우" - {
            val product = Product("name", 1000L, "url")
            val isChanged = product.isChanged(Product("name1", 1000L, "url"))

            isChanged shouldBe true
        }

        "상품의 가격이 변경된 경우" - {
            val product = Product("name", 1000L, "url")
            val isChanged = product.isChanged(Product("name", 100L, "url"))

            isChanged shouldBe true
        }

        "상품의 이미지 URL이 변경된 경우" - {
            val product = Product("name", 1000L, "url")
            val isChanged = product.isChanged(Product("name", 1000L, "ur"))

            isChanged shouldBe true
        }

        "상품이 변경되지 않은 경우" - {
            val product = Product("name", 1000L, "url")
            val isChanged = product.isChanged(Product("name", 1000L, "url"))

            isChanged shouldBe false
        }
    }

    "상품을 수정한다" - {
        "상품의 이름이 변경된 경우" - {
            val product = Product("name", 1000L, "url")
            product.update(name = "name1")

            product.name shouldBe "name1"
            product.price shouldBe 1000L
            product.imageUrl shouldBe "url"
        }

        "상품의 가격이 변경된 경우" - {
            val product = Product("name", 1000L, "url")
            product.update(price = 100L)

            product.name shouldBe "name"
            product.price shouldBe 100L
            product.imageUrl shouldBe "url"
        }

        "상품의 이미지 URL이 변경된 경우" - {
            val product = Product("name", 1000L, "url")
            product.update(imageUrl = "ur")

            product.name shouldBe "name"
            product.price shouldBe 1000L
            product.imageUrl shouldBe "ur"
        }
    }
})
