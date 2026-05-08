package shopping

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import shopping.api.ProductNotFoundException
import shopping.api.ProductRequest
import shopping.api.ProductService
import shopping.api.UpdateRequest
import shopping.domain.ProductName
import shopping.domain.ProductNameFactory

class ProductServiceTest {
    private val productRequest = ProductRequest(
        name = "아이스 카페 아메리카노 T",
        price = 4500,
        imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
    )

    private val productNameFactory = mockk<ProductNameFactory>()
    private val productService = ProductService(productNameFactory)

    @Test
    fun `상품이 추가되어야 한다`() {
        // given
        every { productNameFactory.create(any()) } returns ProductName("아이스 카페 아메리카노 T")

        // when
        val result = productService.addProduct(productRequest)

        // then
        result.name shouldBe "아이스 카페 아메리카노 T"
        result.price shouldBe 4500
    }

    @Test
    fun `상품이 조회되어야 한다`() {
        // given
        every { productNameFactory.create(any()) } returns ProductName("아이스 카페 아메리카노 T")
        productService.addProduct(productRequest)

        // when
        val result = productService.getProducts()

        // then
        result shouldHaveSize 1
        result[0].name shouldBe "아이스 카페 아메리카노 T"
        result[0].price shouldBe 4500
    }

    @Test
    fun `상품 1개만 조회한다`() {
        // given
        every { productNameFactory.create(any()) } returns ProductName("아이스 카페 아메리카노 T")
        val addProductResponse = productService.addProduct(productRequest)

        // when
        val result = productService.getSingleProduct(addProductResponse.id)

        // then
        result.id shouldBe addProductResponse.id
        result.name shouldBe "아이스 카페 아메리카노 T"
    }

    @Test
    fun `상품 조회 실패시 에러 발생`() {
        // then
        shouldThrow<ProductNotFoundException> { productService.getSingleProduct(1) }
    }

    @Test
    fun `상품이 수정되어야 한다`() {
        // given
        every { productNameFactory.create(any()) } returns ProductName("아이스 카페 아메리카노 T")
        val addProductResponse = productService.addProduct(productRequest)

        every { productNameFactory.create(any()) } returns ProductName("콜드브루")
        val updateRequest = UpdateRequest(
            id = addProductResponse.id,
            name = "콜드브루",
            price = 5000,
            imageUrl = productRequest.imageUrl,
        )

        // when
        val result = productService.updateProduct(updateRequest)

        // then
        result.name shouldBe "콜드브루"
        result.price shouldBe 5000
    }

    @Test
    fun `상품이 삭제되어야 한다`() {
    }
}
