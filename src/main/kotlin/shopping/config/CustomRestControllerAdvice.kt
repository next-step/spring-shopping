package shopping.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import shopping.exception.NotFoundException

@RestControllerAdvice
class CustomRestControllerAdvice {
    private val log = LoggerFactory.getLogger(javaClass)

    @ResponseBody
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(): ResponseEntity<String> = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found.")

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.fieldError?.defaultMessage)

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun handleUnknownException(ex: Exception): ResponseEntity<String> {
        log.error("An unexpected error occurred", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.message)
    }
}
