package shopping.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ProductTest {
    private val name = ProductName("아이스 아메리카노")
    private val validUrl = "https://example.com/image.jpg"

    @Test
    fun `http로 시작하는 이미지 URL은 유효하다`() {
        assertDoesNotThrow { Product(1, name, 4500, "http://example.com/image.jpg") }
    }

    @Test
    fun `https로 시작하는 이미지 URL은 유효하다`() {
        assertDoesNotThrow { Product(1, name, 4500, validUrl) }
    }

    @Test
    fun `http 또는 https로 시작하지 않는 이미지 URL은 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> { Product(1, name, 4500, "ftp://example.com/image.jpg") }
    }
}
