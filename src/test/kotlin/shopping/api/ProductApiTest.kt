package shopping.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.web.client.RestClient
import shopping.api.ProductApi.Companion.PRODUCTS_URL
import shopping.api.ProductApi.Companion.PRODUCT_URL
import shopping.dto.ProductFixture
import shopping.dto.ProductRequest
import shopping.service.ProductService

@WebMvcTest(ProductApi::class)
class ProductApiTest(
    private val mockMvc: MockMvc,
    private val mapper: ObjectMapper,
    @MockkBean private val profanityValidator: ProfanityValidator,
    @MockkBean private val restClient: RestClient,
    @MockkBean private val productService: ProductService) : DescribeSpec({

    describe("GET /api/products") {
        it("200을 반환한다") {
            every { productService.getProducts() } returns listOf(
                ProductFixture.of("아이스 아메리카노", 4500)
            )

            mockMvc.get(PRODUCTS_URL)
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0].name") { value("아이스 아메리카노") }
                    jsonPath("$[0].price") { value(4500) }
                }
        }
    }

    describe("GET /api/product/{id}") {
        it("200을 반환한다") {
            every { productService.getProduct(1) } returns ProductFixture.of("아이스 아메리카노", 4500)

            mockMvc.get("${PRODUCT_URL}/1")
                .andExpect {
                    status { isOk() }
                    jsonPath("$.name") { value("아이스 아메리카노") }
                    jsonPath("$.price") { value(4500) }
                }
        }
    }

    describe("POST /api/product") {
        context("유효한 상품 정보가 주어졌을 때") {
            it("201을 반환한다") {
                val product = ProductFixture.of("아이스 아메리카노", 4500)
                every { productService.addProduct(any<ProductRequest>()) } returns product

                mockMvc.post(PRODUCT_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = mapper.writeValueAsString(product)
                }.andExpect {
                        status { isCreated() }
                        header { string("Location", "${PRODUCT_URL}/1") }
                        jsonPath("$.name") { value("아이스 아메리카노") }
                        jsonPath("$.price") { value(4500) }
                    }
            }
        }
    }

    describe("PUT /api/product/{id}") {
        context("유효한 상품 정보가 주어졌을 때") {
            it("200를 반환한다") {
                val product = ProductFixture.of("아이스 아메리카노", 4500)
                every { productService.update(1, any<ProductRequest>()) } returns product

                mockMvc.put("${PRODUCT_URL}/1")  {
                    contentType = MediaType.APPLICATION_JSON
                    content = mapper.writeValueAsString(product)
                }.andExpect {
                        status { isOk() }
                        jsonPath("$.name") { value("아이스 아메리카노") }
                        jsonPath("$.price") { value(4500) }
                    }
            }
        }
    }

    describe("DELETE /api/product/{id}") {
        it("204를 반환한다") {
            every { productService.delete(any()) } just runs

            mockMvc.delete("${PRODUCT_URL}/1")
                .andExpect {
                    status { isNoContent() }
                }
        }
    }
})

