package shopping.product.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import shopping.product.entity.Product
import shopping.product.exception.ProductNotFoundException
import shopping.product.repository.ProductRepository

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest(
    private val productService: ProductService,
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
            val result = productService.findAll()

            // then
            result.size shouldBe 2
            result.any { it.name == "Product1" && it.price == 1000 } shouldBe true
            result.any { it.name == "Product2" && it.price == 2000 } shouldBe true
        }

        test("데이터가 없으면 사이즈가 0이다.") {
            // when
            val result = productService.findAll()

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
            val result = productService.findById(saved.id!!)

            // then
            result shouldNotBe null
            result?.name shouldBe "Product1"
            result?.price shouldBe 1000
            result?.imageUrl shouldBe "http://example.com/1.jpg"
        }

        test("id 가 없으면 null 을 리턴한다.") {
            // when
            val result = productService.findById(999L)

            // then
            result.shouldBeNull()
        }
    }

    context("create") {
        test("저장하고 나면 id 가 생성된다.") {
            // given
            val newProduct = Product(name = "New Product", price = 3000, imageUrl = "http://example.com/new.jpg")

            // when
            val result = productService.create(newProduct)

            // then
            result.id shouldNotBe null
            result.name shouldBe "New Product"
            result.price shouldBe 3000
            result.imageUrl shouldBe "http://example.com/new.jpg"

            // 저장 확인
            val found = productRepository.findById(result.id!!)
            found shouldNotBe null
            found?.name shouldBe "New Product"
        }
    }

    context("update") {
        test("수정할수 있다.") {
            // given
            val existingProduct = Product(name = "Old Product", price = 1000, imageUrl = "http://example.com/old.jpg")
            val saved = productRepository.save(existingProduct)
            val updateData =
                Product(name = "Updated Product", price = 2000, imageUrl = "http://example.com/updated.jpg")

            // when
            val result = productService.update(saved.id!!, updateData)

            // then
            result.name shouldBe "Updated Product"
            result.price shouldBe 2000
            result.imageUrl shouldBe "http://example.com/updated.jpg"
            result.id shouldBe saved.id

            // 실제 저장 확인
            val found = productRepository.findById(saved.id!!)
            found shouldNotBe null
            found?.name shouldBe "Updated Product"
            found?.price shouldBe 2000
        }

        test("id 가 없으면 예외를 발생시킨다.") {
            // given
            val updateData =
                Product(name = "Updated Product", price = 2000, imageUrl = "http://example.com/updated.jpg")

            // when & then
            val exception = shouldThrow<ProductNotFoundException> {
                productService.update(999L, updateData)
            }

            exception.shouldBeInstanceOf<ProductNotFoundException>()
        }
    }

    context("deleteById") {
        test("id 를 통해 삭제한다.") {
            // given
            val product = Product(name = "Product", price = 1000, imageUrl = "http://example.com/1.jpg")
            val saved = productRepository.save(product)

            // when
            productService.deleteById(saved.id!!)

            // then
            val found = productRepository.findById(saved.id!!)
            found.shouldBeNull()
        }

        test("id 가 없으면 예외를 발생시킨다.") {
            // when & then
            val exception = shouldThrow<ProductNotFoundException> {
                productService.deleteById(999L)
            }

            exception.shouldBeInstanceOf<ProductNotFoundException>()
        }
    }
})
