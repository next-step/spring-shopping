package shopping.infra

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate
import shopping.domain.Product
import shopping.domain.ProductName

@JdbcTest
class ProductRepositoryTest {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    private lateinit var productRepository: ProductRepository

    private val product =
        Product(
            id = 0,
            name = ProductName("아이스 카페 아메리카노 T"),
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
        )

    @BeforeEach
    fun setUp() {
        productRepository = ProductRepository(jdbcTemplate)
    }

    @Test
    fun `상품을 추가하면 저장한다`() {
        // when
        val result = productRepository.save(product)

        // then
        result.name.name shouldBe "아이스 카페 아메리카노 T"
        result.price shouldBe 4500
    }

    @Test
    fun `상품을 저장하면 조회할 수 있다`() {
        // given
        val saved = productRepository.save(product)

        // when
        val result = productRepository.findById(saved.id)

        // then
        result?.name?.name shouldBe "아이스 카페 아메리카노 T"
        result?.price shouldBe 4500
    }

    @Test
    fun `상품 목록을 조회할 수 있다`() {
        // given
        productRepository.save(product)
        productRepository.save(product)

        // when
        val result = productRepository.findAll()

        // then
        result shouldHaveSize 2
    }

    @Test
    fun `상품을 수정할 수 있다`() {
        // given
        val saved = productRepository.save(product)
        val updated = Product(saved.id, ProductName("콜드브루"), 5000, product.imageUrl)

        // when
        productRepository.update(updated)

        // then
        val result = productRepository.findById(saved.id)
        result?.name?.name shouldBe "콜드브루"
        result?.price shouldBe 5000
    }

    @Test
    fun `상품을 삭제할 수 있다`() {
        // given
        val saved = productRepository.save(product)

        // when
        productRepository.delete(saved.id)

        // then
        val result = productRepository.findById(saved.id)
        result shouldBe null
    }
}
