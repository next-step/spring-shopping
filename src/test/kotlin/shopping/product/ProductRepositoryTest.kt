package shopping.product

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test


class ProductRepositoryTest {
    @Test
    fun `상품 등록 시 정수형 ID 를 채번하여 상품을 생성한다`() {
        val productRepository = ProductRepository()
        val product = productRepository.create(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )
        product.id.shouldNotBeNull()
    }

    @Test
    fun `상품 ID 는 유니크한 값으로 채번한다`() {
        val productRepository = ProductRepository()

        val product1 = productRepository.create(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )
        val product2 = productRepository.create(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )
        product1.id shouldNotBe product2.id
    }
}