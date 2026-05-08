package shopping

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity
import shopping.product.ProductRequest
import shopping.product.ProductResponse
import shopping.product.ProductService

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductE2ETest(
    @LocalServerPort private val port: Int,
    private val builder: RestClient.Builder
) {
private lateinit var client: RestClient

    @BeforeEach
    fun setup() {
        println("Application started! Port is $port")
        client = builder.baseUrl("http://localhost:$port").build()
    }
    @Test
    fun `상품을 1개 조회한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual = client
            .post()
            .uri(PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity<Unit>()

        actual.statusCode shouldBe HttpStatus.CREATED

        val url = actual.headers.location.toString()
        url shouldContain PRODUCT_PATH

        val actual2 = client
            .get()
            .uri(url)
            .retrieve()
            .toEntity<ProductResponse>()

        actual2.statusCode shouldBe HttpStatus.OK
    }

    @Test
    fun `상품을 여러개 조회한다`() {
        // when
        val actual = client.get()
            .uri(PRODUCT_PATH)
            .retrieve()
            .toEntity<List<ProductResponse>>()

        // then
        actual.statusCode shouldBe HttpStatus.OK
        val response = actual.body
        response.shouldNotBeNull()
//        response.shouldHaveSize(0)
    }

    @Test
    fun `상품을 1개 등록한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual = client
            .post()
            .uri(PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity<Unit>()

        actual.statusCode shouldBe HttpStatus.CREATED
        actual.headers.location.toString() shouldContain PRODUCT_PATH
    }

    @Test
    fun `상품을 1개 등록 후 상품을 여러 개 조회한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        client
            .post()
            .uri(PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toBodilessEntity()

        val response = ProductResponse(
            1L,
            "아이스 카페 아메리카노 T",
            4500,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual = client
            .get()
            .uri(PRODUCT_PATH)
            .retrieve()
            .toEntity<List<ProductResponse>>()

        actual.statusCode shouldBe HttpStatus.OK
        val body = actual.body
        body.shouldNotBeNull()
        body.shouldHaveSize(1)
        body.shouldContain(response)
    }

    @Test
    fun `상품을 1개 수정한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual = client
            .post()
            .uri(PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity<Unit>()

        actual.statusCode shouldBe HttpStatus.CREATED
        val url = actual.headers.location.toString()
        url shouldContain PRODUCT_PATH

        val requestEdit = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 5500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual2 = client
            .put()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestEdit)
            .retrieve()
            .toEntity<Unit>()

        actual2.statusCode shouldBe HttpStatus.NO_CONTENT

        val actual3 = client
            .get()
            .uri(url)
            .retrieve()
            .toEntity<ProductResponse>()

        actual3.statusCode shouldBe HttpStatus.OK
        actual3.body.shouldNotBeNull()
        actual3.body.price shouldBe 5500
    }

   @Test
    fun `상품을 1개 등록하고 그 상품을 삭제한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual = client
            .post()
            .uri(PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity<Unit>()

        actual.statusCode shouldBe HttpStatus.CREATED
        val url = actual.headers.location.toString()
        url shouldContain PRODUCT_PATH

        val actual2 = client
            .delete()
            .uri(url)
            .retrieve()
            .toEntity<Unit>()

        actual2.statusCode shouldBe HttpStatus.NO_CONTENT
    }

    @Test
    fun `유효하지 않은 id로 수정할 때 IllegalArgumentException 예외를 발생한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )
        val url = "$PRODUCT_PATH/123"
        val actual = client
            .put()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .onStatus({ it.is4xxClientError }){_,_->}
            .toEntity<Unit>()

        actual.statusCode.is4xxClientError shouldBe true
    }

    @Test
    fun `유효하지 않은 id로 삭제할 때 IllegalArgumentException 예외를 발생한다`() {
        val url = "$PRODUCT_PATH/123"
        val actual = client
            .delete()
            .uri(url)
            .retrieve()
            .onStatus({ it.is4xxClientError }){_,_->}
            .toEntity<Unit>()

        actual.statusCode.is4xxClientError shouldBe true
    }

    @Test
    fun `상품 이미지는 URL이 아니면 에러를 발생한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual = client
            .post()
            .uri(PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .onStatus({ it.is4xxClientError }){_,_->}
            .toEntity<Unit>()

        actual.statusCode.is4xxClientError shouldBe true
    }
    @Test
    fun `수정할 때 상품 이름은 공백 포함 15자를 초과하면 에러가 발생한다`() {
        val request = ProductRequest(
            name = "아이스 카페 아메리카노 T",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual = client
            .post()
            .uri(PRODUCT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity<Unit>()

        actual.statusCode shouldBe HttpStatus.CREATED
        val url = actual.headers.location.toString()
        url shouldContain PRODUCT_PATH

        val requestEdit = ProductRequest(
            name = "아이스 카페 아메리카노 Venti",
            price = 4500,
            imageUrl = "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        )

        val actual2 = client
            .put()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestEdit)
            .retrieve()
            .onStatus({ it.is4xxClientError }){_,_->}
            .toEntity<Unit>()

        actual2.statusCode.is4xxClientError shouldBe true
    }

}