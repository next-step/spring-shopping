package shopping

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity
import shopping.config.ProductRepository
import shopping.domain.Product

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductE2ETest(
    @LocalServerPort private val port: Int,
    private val builder: RestClient.Builder,
    private val productRepository: ProductRepository
) {
    private lateinit var client: RestClient

    @BeforeEach
    fun setUp() {
        client = builder.baseUrl("http://localhost:$port").build()
        productRepository.deleteAll()  // 각 테스트 전 초기화
    }

    @Test
    fun `Product전체 조회`() {
        val actual = client.get()
            .uri("/api/products")
            .retrieve()
            .toEntity<List<ProductResponse>>()

        actual.statusCode shouldBe HttpStatus.OK
        actual.body.shouldNotBeNull()
        actual.body!! shouldHaveSize 0
    }

    @Test
    fun `Product추가 실패`() {
        val request = ProductRequest("카페 아메리카노 T shit", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        val actual = client.post()
            .uri("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .onStatus({ it.is4xxClientError }) { _, _ -> }
            .toEntity(String::class.java)

        actual.statusCode shouldBe HttpStatus.BAD_REQUEST
    }

    @Test
    fun `Product 추가 후 조회`() {
        val request = ProductRequest("아이스 카페 아메리카노 T", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        val created = client.post()
            .uri("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(ProductResponse::class.java)

        val actual = client.get()
            .uri("/api/products")
            .retrieve()
            .toEntity<List<ProductResponse>>()

        actual.statusCode shouldBe HttpStatus.OK
        val response = actual.body.shouldNotBeNull()
        response shouldHaveSize 1
        response[0].name shouldBe "아이스 카페 아메리카노 T"
    }

    @Test
    fun `Product 추가 후 수정`() {
        val created = client.post()
            .uri("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .body(ProductRequest("아이스 카페 아메리카노 T", 4500,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"))
            .retrieve()
            .toEntity(ProductResponse::class.java)

        val id = created.body!!.id

        val actual = client.put()
            .uri("/api/product/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .body(ProductRequest("아이스 카페 아메리카노 T2", 4500,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"))
            .retrieve()
            .toEntity(ProductResponse::class.java)

        actual.statusCode shouldBe HttpStatus.OK
        val response = actual.body.shouldNotBeNull()
        response.name shouldBe "아이스 카페 아메리카노 T2"
        response.price shouldBe 4500
    }

    @Test
    fun `Product 추가 후 삭제`() {
        val created = client.post()
            .uri("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .body(ProductRequest("아이스 카페 아메리카노 T", 4500,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"))
            .retrieve()
            .toEntity(ProductResponse::class.java)

        val id = created.body!!.id

        val actual = client.delete()
            .uri("/api/product/$id")
            .retrieve()
            .toBodilessEntity()

        actual.statusCode shouldBe HttpStatus.NO_CONTENT
        productRepository.findById(id).isEmpty shouldBe true
    }
}