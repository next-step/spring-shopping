package shopping

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import shopping.config.ProductRepository
import shopping.domain.Product
import java.util.Optional

@ExtendWith(MockKExtension::class)
class ProductServiceTest : BehaviorSpec({

    val productRepository = mockk<ProductRepository>()
    val productService = ProductService(productRepository)

    given("상품 목록 조회") {
        `when`("상품이 존재할 때") {
            val products = listOf(
                Product(id = 1L, name = "아이스 아메리카노", price = 4500, imageUrl = "https://image.url"),
                Product(id = 2L, name = "카페 라떼", price = 5000, imageUrl = "https://image.url")
            )
            every { productRepository.findAll() } returns products

            then("전체 상품을 반환한다") {
                val result = productService.genProducts()
                result shouldHaveSize 2
                result[0].name shouldBe "아이스 아메리카노"
                result[1].name shouldBe "카페 라떼"
            }
        }

        `when`("상품이 없을 때") {
            every { productRepository.findAll() } returns emptyList()

            then("빈 리스트를 반환한다") {
                val result = productService.genProducts()
                result shouldHaveSize 0
            }
        }
    }

    given("상품 추가") {
        `when`("유효한 상품 정보가 주어졌을 때") {
            val request = ProductRequest("아이스 아메리카노", 4500, "https://image.url")
            val savedProduct = Product(id = 1L, name = "아이스 아메리카노", price = 4500, imageUrl = "https://image.url")
            every { productRepository.save(any()) } returns savedProduct

            then("상품을 저장하고 반환한다") {
                val result = productService.addProduct(request)
                result.name shouldBe "아이스 아메리카노"
                result.price shouldBe 4500
                verify { productRepository.save(any()) }
            }
        }
    }

    given("상품 수정") {
        `when`("존재하는 상품 id가 주어졌을 때") {
            val product = Product(id = 1L, name = "아이스 아메리카노", price = 4500, imageUrl = "https://image.url")
            val request = ProductRequest("아이스 카페 아메리카노", 5000, "https://image.url")
            every { productRepository.findById(1L) } returns Optional.of(product)

            then("상품을 수정하고 반환한다") {
                val result = productService.update(1L, request)
                result.name shouldBe "아이스 카페 아메리카노"
                result.price shouldBe 5000
            }
        }

        `when`("존재하지 않는 상품 id가 주어졌을 때") {
            every { productRepository.findById(999L) } returns Optional.empty()

            then("IllegalArgumentException을 던진다") {
                shouldThrow<IllegalArgumentException> {
                    productService.update(999L, ProductRequest("아이스 카페 아메리카노", 5000, "https://image.url"))
                }
            }
        }
    }

    given("상품 삭제") {
        `when`("존재하는 상품 id가 주어졌을 때") {
            every { productRepository.deleteById(1L) } just Runs

            then("상품을 삭제한다") {
                shouldNotThrowAny { productService.delete(1L) }
                verify { productRepository.deleteById(1L) }
            }
        }
    }
})