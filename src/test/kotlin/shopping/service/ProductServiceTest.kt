package shopping.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import shopping.controller.dto.ProductRequest

@SpringBootTest
class ProductServiceTest : BehaviorSpec() {
    @Autowired
    lateinit var productService: ProductService

    init {
        Given("상품을 관리하는 Service") {
            val product = ProductRequest("name", 1000L, "url")
            val id = productService.save(product)
            Then("상품이 정상적으로 저장된다.") {
                id shouldNotBe null
            }

            When("상품을 조회한다.") {
                val result = productService.getById(id)
                Then("상품이 정상적으로 조회된다.") {
                    result.run {
                        name shouldBe "name"
                        price shouldBe 1000L
                        imageUrl shouldBe "url"
                    }
                }
            }

            When("상품을 수정한다.") {
                val updateProduct = ProductRequest("name2", 2000L, "url2")
                productService.update(id, updateProduct)
                Then("상품 조회시 수정된 데이터가 조회된다.") {
                    productService.getById(id).run {
                        name shouldBe "name2"
                        price shouldBe 2000L
                        imageUrl shouldBe "url2"
                    }
                }
            }

            When("상품을 삭제한다.") {
                productService.delete(id)
                Then("상품이 조회되지 않는다.") {
                    val exception = shouldThrow<IllegalArgumentException> { productService.getById(id) }
                    exception.message shouldBe "상품이 존재하지 않습니다."
                }
            }
        }
    }
}
