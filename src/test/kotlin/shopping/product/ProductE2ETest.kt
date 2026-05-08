package shopping.product

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductE2ETest(
    @LocalServerPort private val port: Int,
    @Autowired private val builder: RestClient.Builder,
) {
    val client = builder.baseUrl("http://localhost:$port").build()

    @Test
    fun `상품이 없을 때 상품 목록을 조회하면 200과 빈 리스트를 반환한다`() {
        val actual =
            client
                .get()
                .uri(PRODUCT_PATH)
                .retrieve()
                .toEntity<List<ProductResponse>>()

        actual.statusCode shouldBe HttpStatus.OK
        actual.body shouldBe emptyList()
    }

    @Test
    fun `상품을 등록한다`() {
        val request = createProductRequest()
        val actual =
            client
                .post()
                .uri(PRODUCT_PATH)
                .body(request)
                .retrieve()
                .toBodilessEntity()

        actual.statusCode shouldBe HttpStatus.CREATED
        actual.headers.location.toString() shouldContain PRODUCT_PATH
    }

    @Test
    fun `상품을 등록후 수정하고 다시 조회한다`() {
        val addActual =
            client
                .post()
                .uri(PRODUCT_PATH)
                .body(createProductRequest())
                .retrieve()
                .toBodilessEntity()

        addActual.statusCode shouldBe HttpStatus.CREATED

        val uri = addActual.headers.location.toString()
        uri shouldContain PRODUCT_PATH

        val updateActual =
            client
                .put()
                .uri(uri)
                .body(createProductRequest(name = "따뜻한 아메리카노"))
                .retrieve()
                .toEntity<Unit>()

        updateActual.statusCode shouldBe HttpStatus.NO_CONTENT

        val retrieveActual =
            client
                .get()
                .uri(uri)
                .retrieve()
                .toEntity<Unit>()
        retrieveActual.statusCode shouldBe HttpStatus.OK
    }

    @Test
    fun `상품을 등록후 수정하고 삭제한다`() {
        val addActual =
            client
                .post()
                .uri(PRODUCT_PATH)
                .body(createProductRequest())
                .retrieve()
                .toBodilessEntity()

        addActual.statusCode shouldBe HttpStatus.CREATED

        val uri = addActual.headers.location.toString()
        uri shouldContain PRODUCT_PATH

        val deleteActual =
            client
                .delete()
                .uri(uri)
                .retrieve()
                .toEntity<Unit>()

        deleteActual.statusCode shouldBe HttpStatus.NO_CONTENT
    }
}

private fun createProductRequest(
    name: String = "아이스 아메리카노",
    price: Int = 4500,
    imageUrl: String = "https://example.com/image.jpg",
): ProductRequest =
    ProductRequest(
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
