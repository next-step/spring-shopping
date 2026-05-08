package shopping

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import shopping.domain.Product

class ProductTest {

    @Test
    fun `상품 이름은 공백을 포함하여 최대 15자까지이다`(){
        shouldNotThrowAny { ProductFixture.of("아이스아메리카노") }
        shouldThrow<IllegalArgumentException> { ProductFixture.of("아이스아메리카노아이스아메리카노카페라떼") }

    }

    @ValueSource(strings = ["[아메리카노]","에스프레소+카페라떼"])
    @ParameterizedTest
    fun `특수문자는 일부만 가능하다`(param: String) {
        shouldNotThrowAny { ProductFixture.of(param) }
    }

    @ValueSource(strings = ["*아메리카노*","<에스프레소+카페라떼>","★",","])
    @ParameterizedTest
    fun `특수문자는 일부 이외에는 불가능하다`(param: String) {
        shouldThrow<IllegalArgumentException> { ProductFixture.of(param) }
    }
}

class ProductFixture() {
    companion object {
        fun of(name: String): Product {
            return Product(1L, name, 0, "")
        }
    }
}
