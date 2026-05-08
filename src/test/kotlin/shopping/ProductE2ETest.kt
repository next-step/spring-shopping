package shopping

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.BeforeEach
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.client.toEntity
import kotlin.test.Test

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
open class ProductE2ETest(
    @LocalServerPort private val port: Int,
    private val builder: RestClient.Builder,
) {
    @MockitoBean
    private lateinit var productService: ProductService

    private lateinit var client: RestClient

    @BeforeEach
    open fun setup() {
        client = builder.baseUrl("http://localhost:$port").build()
    }

    @Test
    open fun test1() {
        // when
        val actual =
            client
                .get()
                .uri("/api/products")
                .retrieve()
                .toEntity<List<ProductResponse>>()

        // then
        actual.statusCode shouldBe HttpStatus.OK
        val response = actual.body
        response.shouldNotBeNull()
        response.shouldHaveSize(0)
    }

    @Test
    open fun test2() {
        // given
        val response =
            ProductResponse(
                8146027,
                "아이스 카페 아메리카노 T",
                4500,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
            )

        given(productService.getProducts()).willReturn(listOf(response))

        // when
        val actual =
            client
                .get()
                .uri("/api/products")
                .retrieve()
                .toEntity<List<ProductResponse>>()

        // then
        actual.statusCode shouldBe HttpStatus.OK
        val body = actual.body
        body.shouldNotBeNull()
        body.shouldHaveSize(1)
        body.shouldContain(response)
    }

    @Test
    open fun test3() {
        // given
        val request =
            ProductRequest(
                "아이스 카페 아메리카노 T",
                4500,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
            )
        val response = ProductResponse(1L, request.name, request.price, request.imageUrl)
        given(productService.addProduct(any())).willReturn(response)

        // when
        val actual =
            client
                .post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity()

        // then
        actual.statusCode shouldBe HttpStatus.CREATED
        actual.headers.location.toString() shouldContain "/api/products"
    }
}
