package shopping

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import shopping.config.ProductRepository
import shopping.domain.Product
import java.util.Optional

class ProductServiceTest2 : StringSpec({

    val productRepository = mockk<ProductRepository>()
    val productService = ProductService(productRepository)

    "상품 목록이 존재할 때 전체 상품을 반환한다" {
        val products = listOf(
            Product(id = 1L, name = "아이스 아메리카노", price = 4500, imageUrl = "https://image.url"),
            Product(id = 2L, name = "카페 라떼", price = 5000, imageUrl = "https://image.url")
        )
        every { productRepository.findAll() } returns products

        val result = productService.genProducts()
        result shouldHaveSize 2
        result[0].name shouldBe "아이스 아메리카노"
        result[1].name shouldBe "카페 라떼"
    }

    "상품이 없을 때 빈 리스트를 반환한다" {
        every { productRepository.findAll() } returns emptyList()

        val result = productService.genProducts()
        result shouldHaveSize 0
    }

    "유효한 상품 정보로 상품을 추가한다" {
        val request = ProductRequest("아이스 아메리카노", 4500, "https://image.url")
        val savedProduct = Product(id = 1L, name = "아이스 아메리카노", price = 4500, imageUrl = "https://image.url")
        every { productRepository.save(any()) } returns savedProduct

        val result = productService.addProduct(request)
        result.name shouldBe "아이스 아메리카노"
        result.price shouldBe 4500
        verify { productRepository.save(any()) }
    }

    "존재하는 상품 id로 상품을 수정한다" {
        val product = Product(id = 1L, name = "아이스 아메리카노", price = 4500, imageUrl = "https://image.url")
        val request = ProductRequest("아이스 카페 아메리카노", 5000, "https://image.url")
        every { productRepository.findById(1L) } returns Optional.of(product)

        val result = productService.update(1L, request)
        result.name shouldBe "아이스 카페 아메리카노"
        result.price shouldBe 5000
    }

    "존재하지 않는 상품 id로 수정 시 IllegalArgumentException을 던진다" {
        every { productRepository.findById(999L) } returns Optional.empty()

        shouldThrow<IllegalArgumentException> {
            productService.update(999L, ProductRequest("아이스 카페 아메리카노", 5000, "https://image.url"))
        }
    }

    "존재하는 상품 id로 상품을 삭제한다" {
        every { productRepository.deleteById(1L) } just Runs

        shouldNotThrowAny { productService.delete(1L) }
        verify { productRepository.deleteById(1L) }
    }
})