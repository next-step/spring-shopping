package shopping.repository

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import shopping.domain.Product

class ProductRepositoryTest :
    BehaviorSpec({

        Given("Product를 생성한다.") {
            val productRepository = ProductRepository()

            val originName = "apple"
            val originPrice = 1000L
            val originUri = "url"
            val id = productRepository.save(Product(originName, originPrice, originUri))

            When("Product를 조회한다.") {
                val product = productRepository.getById(1L)

                Then("Product가 정상 조회된다.") {
                    product.run {
                        name shouldBe originName
                        price shouldBe originPrice
                        imageUrl shouldBe originUri
                    }
                }

                When("Product를 수정한다.") {
                    val updateName = "banana"
                    val updatePrice = 5000L
                    val updateUrl = "url123"
                    productRepository.update(id, Product(updateName, updatePrice, updateUrl))

                    Then("Product가 정상 수정된다.") {
                        productRepository.getById(id).run {
                            name shouldBe updateName
                            price shouldBe updatePrice
                            imageUrl shouldBe updateUrl
                        }
                    }
                }

                When("Product가 제거된다.") {
                    productRepository.delete(id)

                    Then("Product가 조회되지 않는다.") {
                        val exception = shouldThrow<IllegalArgumentException> { productRepository.getById(id) }
                        exception.message shouldBe "상품이 존재하지 않습니다."
                    }
                }
            }
        }
    })
