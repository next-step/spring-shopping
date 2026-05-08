package shopping

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

    private val product = Product(
        id = 1,
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
        val result = productRepository.saveProduct(product)

        // then
        result.name.name shouldBe "아이스 카페 아메리카노 T"
        result.price shouldBe 4500
    }

    @Test
    fun `상품을 저장하면 조회할 수 있다`() {
        // given
        productRepository.saveProduct(product)

        // when
        val result = productRepository.getById(product.id)

        // then
        result?.name?.name shouldBe "아이스 카페 아메리카노 T"
        result?.price shouldBe 4500
    }
}
