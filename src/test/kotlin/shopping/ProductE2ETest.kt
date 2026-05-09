package shopping

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import shopping.api.ProductApi.Companion.PRODUCT_URL
import shopping.domain.Product
import shopping.dto.ProductRequest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductE2ETest(
    @LocalServerPort private val port: Int
) : BehaviorSpec({
    val client = RestClient.builder()
        .baseUrl("http://localhost:$port")
        .build()

    given("상품 관리") {
        `when`("상품을 추가하면") {
            val created = client.post()
                .uri(PRODUCT_URL)
                .body(
                    ProductRequest("아메리카노", 1000, "image.com")
                )
                .retrieve()
                .body(Product::class.java)!!

            then("조회 시 추가된 상품이 보인다") {
                val response = client.get()
                    .uri("${PRODUCT_URL}/${created.id}")
                    .retrieve()
                    .body(Product::class.java)!!

                response.name shouldBe "아메리카노"
                response.price shouldBe 1000
                response.imageUrl shouldBe "image.com"
            }
        }
        `when`("상품을 수정하면") {
            val created = client.post()
                .uri(PRODUCT_URL)
                .body(
                    ProductRequest("아메리카노", 1000, "image.com")
                )
                .retrieve()
                .body(Product::class.java)!!

            client.put()
                .uri("${PRODUCT_URL}/${created.id}")
                .body(
                    ProductRequest("아메리카노", 5000, "new-image.com")
                )
                .retrieve()
                .toBodilessEntity()

            then("조회 시 수정된 상품이 보인다") {
                val updated = client.get()
                    .uri("${PRODUCT_URL}/${created.id}")
                    .retrieve()
                    .body(Product::class.java)!!

                updated.name shouldBe "아메리카노"
                updated.price shouldBe 5000
                updated.imageUrl shouldBe "new-image.com"
            }
        }
        `when`("상품을 삭제하면") {
            val created = client.post()
                .uri(PRODUCT_URL)
                .body(
                    ProductRequest("삭제상품", 1000, "image.com")
                )
                .retrieve()
                .body(Product::class.java)!!

            client.delete()
                .uri("${PRODUCT_URL}/${created.id}")
                .retrieve()
                .toBodilessEntity()

            then("조회 시 삭제된 상품이 보이지 않는다") {
                val exception = shouldThrow<HttpClientErrorException.NotFound> {
                    client.get()
                        .uri("${PRODUCT_URL}/${created.id}")
                        .retrieve()
                        .body(Product::class.java)
                }

                exception.statusCode shouldBe HttpStatus.NOT_FOUND
            }
        }
        `when`("비속어가 포함된 상품을 추가하면") {
            then("400을 반환한다") {
                val exception = shouldThrow<HttpClientErrorException.BadRequest> {
                    client.post()
                        .uri(PRODUCT_URL)
                        .body(
                            ProductRequest("shit americano", 1000, "image.com")
                        )
                        .retrieve()
                        .body(String::class.java)

                }

                exception.statusCode shouldBe HttpStatus.BAD_REQUEST
            }
        }
    }
}
)