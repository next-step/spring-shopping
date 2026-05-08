package shopping.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class ProductTest {
    @Test
    fun `상품은 상품명, 가격, image url을 가져야 한다`() {
        val product = Product(
            name = "productname",
            price = 10000,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        product.name shouldNotBe null
        product.price shouldNotBe null
        product.imageUrl shouldNotBe null
    }

    @Test
    fun `상품명이 없다면 에러가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            Product(
                name = "",
                price = 10000,
                imageUrl = "https://example.com/photos/200",
            )
        }
    }

    @Test
    fun `상품명은 공백 포함 15자 이하여야 한다`() {
        shouldNotThrowAny {
            Product(
                name = "product name",
                price = 10000,
                imageUrl = "https://example.com/photos/200",
            )
        }
    }

    @Test
    fun `상품명은 공백 포함 15자 초과라면 에러가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            Product(
                name = "product name 123",
                price = 10000,
                imageUrl = "https://example.com/photos/200",
            )
        }
    }

    @Test
    fun `가격이 음수라면 에러가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            Product(
                name = "productname",
                price = -100,
                imageUrl = "https://example.com/photos/200"
            )
        }
    }

    @Test
    fun `상품 이미지가 없다면 에러가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            Product(
                name = "productname",
                price = 10000,
                imageUrl = "",
            )
        }
    }
}