package shopping.web.hello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import shopping.application.hello.HelloService

@RestController
class HelloController (
    private val helloService: HelloService
) {
    @GetMapping("/hello")
    fun hello(): String {
        return helloService.hello()
    }
}