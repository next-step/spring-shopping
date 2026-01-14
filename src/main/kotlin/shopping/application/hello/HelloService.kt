package shopping.application.hello

import org.springframework.stereotype.Service
import shopping.core.hello.Hello

@Service
class HelloService {
    fun hello(): String {

        val hello = Hello("sson, yebink")
        return "Hello World! ${hello}"
    }
}