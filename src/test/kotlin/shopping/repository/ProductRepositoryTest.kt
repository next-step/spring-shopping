package shopping.repository

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import shopping.domain.Product

@SpringBootTest
class ProductRepositoryTest : BehaviorSpec() {
    @Autowired
    lateinit var productRepository: ProductRepository

    init {

        Given("Product를 저장하면") {
            val product = productRepository.save(Product("apple", 1000L, "url"))

            When("id로 조회하면") {
                val found = productRepository.findById(product.id!!).orElseThrow()

                Then("저장된 Product가 조회된다") {
                    found.name shouldBe "apple"
                    found.price shouldBe 1000L
                    found.imageUrl shouldBe "url"
                }
            }
        }

        Given("Product를 저장하고") {
            val product = productRepository.save(Product("apple", 1000L, "url"))

            When("수정하면") {
                product.update(Product("banana", 5000L, "url123"))
                productRepository.save(product)

                Then("변경사항이 반영된다") {
                    val updated = productRepository.findById(product.id!!).orElseThrow()
                    updated.name shouldBe "banana"
                    updated.price shouldBe 5000L
                    updated.imageUrl shouldBe "url123"
                }
            }
        }

        Given("Product를 저장하고") {
            val product = productRepository.save(Product("apple", 1000L, "url"))

            When("삭제하면") {
                productRepository.delete(product)

                Then("조회되지 않는다") {
                    shouldThrow<NoSuchElementException> {
                        productRepository.findById(product.id!!).orElseThrow()
                    }
                }
            }
        }
    }
}
