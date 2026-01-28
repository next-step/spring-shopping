package shopping.product.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import shopping.product.entity.Product
import shopping.product.repository.ProductRepository

@SpringBootTest
@ActiveProfiles("test")
class ProductQueryServiceTest(
    private val productQueryService: ProductQueryService,
    private val productRepository: ProductRepository,
) : FunSpec({

    beforeEach {
        productRepository.deleteAll()
    }

    context("findAll") {
        test("모든 리스트를 조회한다.") {
            // given
            val product1 = Product(name = "Product1", price = 1000, imageUrl = "http://example.com/1.jpg")
            val product2 = Product(name = "Product2", price = 2000, imageUrl = "http://example.com/2.jpg")

            productRepository.save(product1)
            productRepository.save(product2)

            // when
            val result = productQueryService.findAll()

            // then
            result.size shouldBe 2
            result.any { it.name == "Product1" && it.price == 1000 } shouldBe true
            result.any { it.name == "Product2" && it.price == 2000 } shouldBe true
        }

        test("데이터가 없으면 사이즈가 0이다.") {
            // when
            val result = productQueryService.findAll()

            // then
            result.size shouldBe 0
        }
    }

    context("findById") {
        test("id 를 통해 요청한다.") {
            // given
            val product = Product(name = "Product1", price = 1000, imageUrl = "http://example.com/1.jpg")
            val saved = productRepository.save(product)

            // when
            val result = productQueryService.getById(saved.id!!)

            // then
            result shouldNotBe null
            result.name shouldBe "Product1"
            result.price shouldBe 1000
            result.imageUrl shouldBe "http://example.com/1.jpg"
        }
    }
})
