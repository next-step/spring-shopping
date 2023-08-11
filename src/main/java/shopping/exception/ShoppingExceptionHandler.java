package shopping.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ShoppingExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ShoppingExceptionHandler.class);

    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithShoppingApiException(final ShoppingException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithAuthException(final AuthException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithMethodNotSupported(final HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(HttpServletResponse.SC_METHOD_NOT_ALLOWED)
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithMediaTypeNotSupported(final HttpMediaTypeNotSupportedException e) {
        return ResponseEntity
                .status(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE)
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException e) {
        return ResponseEntity
                .status(HttpServletResponse.SC_NOT_ACCEPTABLE)
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler({
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            BindException.class
    })
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithWebBadRequest(final Exception e) {
        return ResponseEntity
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class
    })
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithNoHandlerFoundException(final NoHandlerFoundException e) {
        return ResponseEntity
                .status(HttpServletResponse.SC_NOT_FOUND)
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handle(final Exception e) {
        log.error("Exception", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiExceptionResponse.from("처리할 수 없는 예외가 발생했습니다."));
    }
}
