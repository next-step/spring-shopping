package shopping.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import shopping.dto.ProductFixture
import shopping.dto.ProductRequest
import shopping.repository.ProductRepository
import java.util.*

class ProductServiceTest(
    @MockkBean private val repository: ProductRepository) : BehaviorSpec({
    given("상품 조회") {
        `when`("상품이 존재할 때") {
            then("전체 상품을 반환한다") {
                // given
                every { repository.findAll() } returns listOf(ProductFixture.of("아메리카노", 1000))

                // when
                val result = ProductService(repository).getProducts()

                // then
                result.size shouldBe 1
                result[0].name shouldBe "아메리카노"
                result[0].price shouldBe 1000
            }
        }
        `when`("상품이 없을 때") {
            then("빈 리스트를 반환한다") {
                // given
                every { repository.findAll() } returns listOf()

                // when
                val result = ProductService(repository).getProducts()

                // then
                result.size shouldBe 0
            }
        }
    }
    given("상품 추가") {
        `when`("유효한 상품 정보가 주어졌을 때") {
            then("상품을 저장하고 반환한다") {
                // given
                every { repository.save(any()) } answers { firstArg() }

                // when
                val result = ProductService(repository).addProduct(
                    ProductRequest("카페라떼", 2000, "test.com")
                )

                // then
                result.name shouldBe "카페라떼"
                result.price shouldBe 2000
                result.imageUrl shouldBe "test.com"
            }
        }
    }
    given("상품 수정") {
        `when`("존재하는 id가 주어졌을 때") {
            then("상품을 수정한다") {
                // given
                every { repository.findById(any()) } returns Optional.of(ProductFixture.of("아메리카노", 1000))
                every { repository.save(any()) } answers { firstArg() }

                // when
                val result = ProductService(repository).update(
                    1, ProductRequest("아메리카노", 1500, "test.com")
                )

                // then
                result.name shouldBe "아메리카노"
                result.price shouldBe 1500
                result.imageUrl shouldBe "test.com"
            }
        }
        `when`("존재하지 않는 id가 주어졌을 때") {
            then("예외를 던진다") {
                every { repository.findById(any()) } returns Optional.empty()

                // when
                shouldThrow<NoSuchElementException> {
                    ProductService(repository).update(
                        1, ProductRequest("아메리카노", 1500, "test.com")
                    )
                }

            }
        }
    }
    given("상품 삭제") {
        `when`("존재하는 id가 주어졌을 때") {
            then("상품을 삭제한다") {
                every { repository.findById(any()) } returns Optional.of(ProductFixture.of("아메리카노", 1000))
                every { repository.deleteById(any()) } just runs

                // when
                shouldNotThrowAny { ProductService(repository).delete(1) }
            }
        }
    }
})