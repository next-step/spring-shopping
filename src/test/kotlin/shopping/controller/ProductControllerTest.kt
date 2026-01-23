package shopping.controller

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import shopping.controller.dto.ProductRequest
import shopping.controller.dto.ProductResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest : BehaviorSpec() {
    @LocalServerPort
    var port: Int = 0

    init {
        val restTemplate = TestRestTemplate()

        Given("상품 API") {
            val productUri = "http://localhost:$port/v1/products"
            val response =
                restTemplate.postForEntity(
                    productUri,
                    ProductRequest("name", 1000L, "url"),
                    Void::class.java,
                )
            Then("상품 등록 성공") {
                response.statusCode.value() shouldBe 200
            }

            When("상품 조회 요청") {
                val response =
                    restTemplate.getForEntity("$productUri/1", ProductResponse::class.java)
                Then("상품 조회 성공") {
                    response.apply {
                        statusCode.value() shouldBe 200
                        body?.name shouldBe "name"
                        body?.price shouldBe 1000L
                        body?.imageUrl shouldBe "url"
                    }
                }
            }

            When("상품 수정 요청") {
                restTemplate.put("$productUri/1", ProductRequest("name2", 2000L, "url2"))
                Then("상품 수정 성공") {
                    val response =
                        restTemplate.getForEntity("$productUri/1", ProductResponse::class.java)
                    response.body?.apply {
                        name shouldBe "name2"
                        price shouldBe 2000L
                        imageUrl shouldBe "url2"
                    }
                }
            }

            When("상품 삭제 요청") {
                restTemplate.delete("$productUri/1")
                Then("상품 삭제 성공") {
                    val response =
                        restTemplate.getForEntity("$productUri/1", Unit::class.java)
                    response.statusCode.value() shouldBe 500
                }
            }
        }
    }
}
