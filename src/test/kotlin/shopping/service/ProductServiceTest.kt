package shopping.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import shopping.controller.dto.ProductRequest
import shopping.domain.FakeBadWordValidator
import shopping.domain.Product
import shopping.repository.ProductRepository

class ProductServiceTest :
    StringSpec({
        val badWordValidator = FakeBadWordValidator(setOf("나쁜말", "욕설"))
        val productRepository = FakeProductRepository()
        val productService = ProductService(productRepository, badWordValidator)

        "비속어 포함시 상품 저장 실패" {
            val request = ProductRequest("나쁜말상품", 1000, "http://image.url")

            shouldThrow<IllegalArgumentException> {
                productService.save(request)
            }
        }

        "비속어 없으면 상품 저장 성공" {
            val request = ProductRequest("좋은상품", 1000, "http://image.url")

            val id = productService.save(request)

            id shouldBe 1L
        }

        "비속어 포함시 상품 수정 실패" {
            val saveRequest = ProductRequest("좋은상품", 1000, "http://image.url")
            val id = productService.save(saveRequest)

            val updateRequest = ProductRequest("나쁜말상품", 2000, "http://image.url")

            shouldThrow<IllegalArgumentException> {
                productService.update(id, updateRequest)
            }
        }
    })

class FakeProductRepository : ProductRepository() {
    private val products = mutableMapOf<Long, Product>()
    private var sequence = 0L

    override fun save(product: Product): Long {
        val id = ++sequence
        products[id] = product
        return id
    }

    override fun getById(id: Long) = products[id] ?: throw NoSuchElementException()

    override fun update(
        id: Long,
        product: Product,
    ) {
        products[id] = product
    }

    override fun delete(id: Long) {
        products.remove(id)
    }
}
