package shopping

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ShoppingApplication

fun main(vararg args: String) {
    SpringApplication.run(ShoppingApplication::class.java, *args)
}
