package shopping.product

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ProductTest :
    BehaviorSpec({

        Given("상품을 생성할 때") {
            When("이름, 가격, 이미지 URL을 입력하면") {
                Then("상품이 정상 생성된다") {
                    val product =
                        Product(
                            name = "아이스 아메리카노",
                            price = 4500,
                            imageUrl = "https://example.com/image.jpg",
                            profanities = { false }
                        )
                    product.name shouldBe "아이스 아메리카노"
                    product.price shouldBe 4500
                    product.imageUrl shouldBe "https://example.com/image.jpg"
                }
            }
            When("이름이 15자 초과하여 입력하면") {
                Then("상품이 정상 생성되지 않는다.") {
                    shouldThrow<IllegalArgumentException> {
                        Product(
                            name = "a".repeat(16),
                            price = 4500,
                            imageUrl = "https://example.com/image.jpg",
                            profanities = { false }
                        )
                    }
                }
            }

            When("이름에 허용되지 않는 특수 문자를 포함하여 입력하면") {
                Then("상품이 정상 생성되지 않는다.") {
                    shouldThrow<IllegalArgumentException> {
                        Product(
                            name = "아이스 아메리카노$",
                            price = 4500,
                            imageUrl = "https://example.com/image.jpg",
                            profanities = { false }
                        )
                    }
                }
            }

            When("이름에 비속어가 포함되어 있으면") {
                Then("상품이 정상 생성되지 않는다.") {
                    shouldThrow<IllegalArgumentException> {
                        Product(
                            name = "아이스 아메리카노",
                            price = 4500,
                            imageUrl = "example.com/image.jpg",
                            profanities = { true }
                        )
                    }
                }
            }
        }
    })
