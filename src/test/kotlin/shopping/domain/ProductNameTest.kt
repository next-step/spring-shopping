package shopping.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import shopping.domain.ProductName

class ProductNameTest {
    @Test
    fun `상품 이름은 공백 포함 15자이다`() {
        assertDoesNotThrow { ProductName("Genesis80(JK") }
    }

    @Test
    fun `상품 이름은 공백 포함 15자를 넘어가면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> { ProductName("Genesis80(JK)4555") }
    }

    @Test
    fun `상품이름에는 특정 특수문자만 사용이 가능하다`() {
        assertDoesNotThrow { ProductName("Genesis80([GV])") }
    }

    @Test
    fun `상품이름에 지정된 특수문자 외에는 사용이 불가하다`() {
        assertThrows<IllegalArgumentException> { ProductName("Genesis80![GV)") }
    }
}
