package shopping.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import shopping.controller.dto.ProductRequest
import shopping.domain.FakeBadWordValidator
import shopping.repository.ProductRepository

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductServiceTest(
    private val productRepository: ProductRepository,
    private val productTransactionalService: ProductTransactionalService,
) : BehaviorSpec({
        val badWordValidator = FakeBadWordValidator(setOf("나쁜말", "욕설"))
        val productService = ProductService(productTransactionalService, badWordValidator)

        Given("비속어가 포함된 상품 요청") {
            val request = ProductRequest("나쁜말상품", 1000, "http://image.url")

            When("상품을 저장하면") {
                Then("예외가 발생한다") {
                    shouldThrow<IllegalArgumentException> {
                        productService.save(request)
                    }
                }
            }
        }

        Given("정상 상품 요청") {
            val request = ProductRequest("좋은상품", 1000, "http://image.url")

            When("상품을 저장하면") {
                val id = productService.save(request)

                Then("상품이 저장된다") {
                    productRepository.findById(id).isPresent shouldBe true
                }
            }
        }

        Given("상품이 저장되어 있을 때") {
            val saveRequest = ProductRequest("좋은상품", 1000, "http://image.url")
            val id = productService.save(saveRequest)

            When("비속어 포함 이름으로 수정하면") {
                val updateRequest = ProductRequest("나쁜말상품", 2000, "http://image.url")

                Then("예외가 발생한다") {
                    shouldThrow<IllegalArgumentException> {
                        productService.update(id, updateRequest)
                    }
                }
            }
        }
    })
