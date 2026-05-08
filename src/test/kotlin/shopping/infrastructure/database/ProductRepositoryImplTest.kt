package shopping.infrastructure.database

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import mock.ProductMock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import shopping.domain.Product
import shopping.domain.repository.ProductRepository

class ProductRepositoryImplTest {

    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setup() {
        productRepository = ProductRepositoryImpl()
    }

    @Test
    fun `상품을 저장할 수 있다`() {
        // given
        val product = ProductMock.DEFAULT_PRODUCT

        // when
        val productId = productRepository.save(product)

        // then
        productId shouldNotBe null
        productId shouldBe 1L
    }

    @Test
    fun `전체 상품을 조회할 수 있다`() {
        // given
        val products = listOf(
            ProductMock.createMockProduct("노트북", 1500000, "https://example.com/photos/laptop"),
            ProductMock.createMockProduct("무선 마우스", 35000, "https://example.com/photos/mouse"),
            ProductMock.createMockProduct("기계식 키보드", 120000, "https://example.com/photos/keyboard"),
            ProductMock.createMockProduct("모니터", 450000, "https://example.com/photos/monitor")
        )
        products.forEach {
            productRepository.save(it)
        }

        // when
        val findAll = productRepository.findAll()

        // then
        findAll.size shouldBe products.size

    }

    @Test
    fun `상품 id로 상품을 조회할 수 있다`() {
        // given
        val mockProducts = createMockData()

        // when
        val product1 = productRepository.findById(1L)
        val product2 = productRepository.findById(2L)

        // then
        product1.apply {
            id shouldBe mockProducts[0].id
            name shouldBe mockProducts[0].name
            price shouldBe mockProducts[0].price
            imageUrl shouldBe mockProducts[0].imageUrl
        }

        product2.apply {
            id shouldBe mockProducts[1].id
            name shouldBe mockProducts[1].name
            price shouldBe mockProducts[1].price
            imageUrl shouldBe mockProducts[1].imageUrl
        }
    }

    @Test
    fun `상품 조회 시 일치하는 상품 id가 없으면 에러가 발생한다`() {
        // given
        val mockProducts = createMockData()

        // when & then
        shouldThrow<NoSuchElementException> {
            productRepository.findById(5L)
        }
    }

    @Test
    fun `상품 id로 상품을 수정할 수 있다`() {
        // given
        val mockProducts = createMockData()
        val targetProduct = mockProducts[0]
        val updateProduct = ProductMock.createMockProduct("노트북", 100000, "https://example.com/photos/laptop")
        targetProduct.update(updateProduct)

        // when
        productRepository.update(1L, targetProduct)

        // then
        productRepository.findById(1L).apply {
            id shouldBe updateProduct.id
            price shouldBe updateProduct.price
        }
    }

    @Test
    fun `상품 id로 상품을 삭제할 수 있다`() {
        shouldThrow<NoSuchElementException> {
            createMockData()
            productRepository.delete(1L)
            productRepository.findById(1L)
        }
    }

    private fun createMockData(): List<Product> {
        val products = listOf(
            ProductMock.createMockProduct("노트북", 1500000, "https://example.com/photos/laptop"),
            ProductMock.createMockProduct("무선 마우스", 35000, "https://example.com/photos/mouse"),
            ProductMock.createMockProduct("기계식 키보드", 120000, "https://example.com/photos/keyboard"),
            ProductMock.createMockProduct("모니터", 450000, "https://example.com/photos/monitor")
        )

        products.forEach {
            productRepository.save(it)
        }

        return products
    }
}