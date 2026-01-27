package shopping.web.product

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import shopping.core.product.Product
import shopping.core.product.ProductRepository
import shopping.web.product.request.ProductRequest

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val productRepository: ProductRepository,
) : StringSpec() {
    override fun extensions() = listOf(SpringExtension)

    init {
        beforeTest {
            productRepository.deleteAll()
        }

        "상품 생성" {
            val request = ProductRequest("iphone", 100, "url")

            mockMvc.perform(
                post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name").value("iphone"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.imageUrl").value("url"))
        }

        "허용되는 특수문자 입력 성공" {
            val request = ProductRequest("i()[]+- &/_", 300, "url")

            mockMvc.perform(
                post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name").value("i()[]+- &/_"))
        }

        "허용되지 않는 특수문자 입력 시 실패" {
            val request = ProductRequest("i\$\$\$\$\$\$\$", 300, "url")

            mockMvc.perform(
                post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.name").value("입력할 수 있는 특수문자는 ( ), [ ], +, -, &, /, _ 입니다."))
        }

        "상품 이름 15자 초과 시 실패, 상품 가격 음수 입력 시 실패" {
            val request = ProductRequest("iphoneeeeeeeeeeeeeeee", -100, "url")

            mockMvc.perform(
                post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.name").value("이름은 15자리까지 입력할 수 있습니다."))
                .andExpect(jsonPath("$.price").value("금액은 0 이상이어야 합니다."))
        }

        "상품 이름에 비속어 입력 시 실패" {
            val request = ProductRequest("bitch", 300, "url")

            mockMvc.perform(
                post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value("Product name is invalid"))
        }

        "상품 전체 조회" {
            productRepository.save(Product("iphone", 100, "url"))
            productRepository.save(Product("galaxy", 200, "url2"))

            mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.products.length()").value(2))
        }

        "상품 단건 조회" {
            val saved = productRepository.save(Product("iphone", 100, "url"))

            mockMvc.perform(get("/api/products/${saved.id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("iphone"))
                .andExpect(jsonPath("$.price").value(100))
        }

        "상품 수정" {
            val saved = productRepository.save(Product("iphone", 100, "url"))
            val request = ProductRequest("iphone17", 300, "url")

            mockMvc.perform(
                put("/api/products/${saved.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("iphone17"))
                .andExpect(jsonPath("$.price").value(300))
        }

        "상품 삭제" {
            val saved = productRepository.save(Product("iphone", 100, "url"))

            mockMvc.perform(delete("/api/products/${saved.id}"))
                .andExpect(status().isOk)
        }
    }
}
