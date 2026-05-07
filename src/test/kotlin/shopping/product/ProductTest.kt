package shopping.product

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test


class ProductTest {

    @Test
    fun `상품에는 이름과 가격, 이미지가 있다`() {
        val product = Product(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )
        product.name shouldBe "아이스 카페 아메리카노 T"
        product.price shouldBe 4500
        product.imageUrl shouldBe "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    }

    @Test
    fun `상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다`() {
        shouldNotThrowAny {
            Product(
                name = "아이스 카페 아메리카노 T",
                price = 4500,
                imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
            )
        }
    }

    @Test
    fun `상품 이름은 공백을 포함하여 15자 초과하면 에러가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            Product(
                name = "1234567890123456",
                price = 4500,
                imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
            )
        }
    }

    @Test
    fun `상품 이미지는  URL을 입력 받는다`() {
        shouldNotThrowAny {
            Product(
                name = "아이스 카페 아메리카노 T",
                price = 4500,
                imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
            )
        }
    }

    @Test
    fun `상품 이미지는  URL이 아니면 에러를 발생한다`() {
        shouldThrow<IllegalArgumentException> {

            Product(
                name = "아이스 카페 아메리카노 T",
                price = 4500,
                imageUrl = "st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
            )
        }
    }

}