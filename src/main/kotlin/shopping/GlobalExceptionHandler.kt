package shopping

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @ExceptionHandler(Exception::class)
    fun handlerException(e: Exception): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}

