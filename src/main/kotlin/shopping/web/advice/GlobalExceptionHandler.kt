package shopping.web.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun constraintViolationException(e: MethodArgumentNotValidException): ResponseEntity<*> {
        val errorMessageMap = e.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }

        return ResponseEntity<Any?>(errorMessageMap, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun constraintViolationException(e: IllegalArgumentException): ResponseEntity<*> {
        return ResponseEntity<Any?>(mapOf("message" to e.message), HttpStatus.BAD_REQUEST)
    }
}
