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

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductE2ETest(
    @LocalServerPort private val port: Int,
    private val builder: RestClient.Builder
) {
    private lateinit var client: RestClient

    @BeforeEach
    fun setUp() {
        println("Application started! Port is $port")
        client = builder.baseUrl("http://localhost:$port")
            .build()
    }

    @Test
    fun `Product전체 조회`() {
        // when
        val actual = client.get()
            .uri("/api/products")
            .retrieve()
            .toEntity<List<ProductResponse>>()

        // then
        actual.statusCode shouldBe HttpStatus.OK

        val response = actual.body
        response.shouldNotBeNull()
        response shouldHaveSize 0 //초록색 음영은 스마트캐스팅: response가 null이 아니라고 인지
    }

    @Test
    fun `Product추가 실패`() {
        // given
        val request = ProductRequest("카페 아메리카노 T shit", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        // when
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
        // given
        val request = ProductRequest("아이스 카페 아메리카노 T", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        client.post()
            .uri("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toBodilessEntity()

        val body = ProductResponse(1, "아이스 카페 아메리카노 T", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        val actual = client.get()
            .uri("/api/products")
            .retrieve()
            .toEntity<List<ProductResponse>>()

        // then
        actual.statusCode shouldBe HttpStatus.OK

        val response = actual.body
        response.shouldNotBeNull()
        response shouldHaveSize 1
        response shouldContain body
    }

    @Test
    fun `Product 추가 후 수정`() {
        // given
        val request = ProductRequest("아이스 카페 아메리카노 T", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        client.post()
            .uri("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toBodilessEntity()

        val body = ProductResponse(1, "아이스 카페 아메리카노 T", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        val actual = client.put()
            .uri("/api/product/1")
            .body(ProductRequest("아이스 카페 아메리카노 T2", 4500,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"))
            .retrieve()
            .toEntity<ProductResponse>()

        // then
        actual.statusCode shouldBe HttpStatus.OK

        val response = actual.body
        response.shouldNotBeNull()
        response.id shouldBe 1
        response.name shouldBe "아이스 카페 아메리카노 T2"
        response.price shouldBe 4500

    }

    @Test
    fun `Product 추가 후 삭제`() {
        // given
        val request = ProductRequest("아이스 카페 아메리카노 T", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        client.post()
            .uri("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toBodilessEntity()

        val body = ProductResponse(1, "아이스 카페 아메리카노 T", 4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg")

        val actual = client.delete()
            .uri("/api/product/1")
            .retrieve()
            .toBodilessEntity()

        // then
        actual.statusCode shouldBe HttpStatus.NO_CONTENT
    }

}