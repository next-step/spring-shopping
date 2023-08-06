package shopping.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class SpringWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(SpringWebExceptionHandler.class);

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ConversionNotSupportedException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class
    })
    public ResponseEntity<ApiExceptionResponse> handleSpringException(final Exception e) {
        return ResponseEntity
                .status(mappingSpringWebExceptionToStatusCode(e))
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handle(final Exception e) {
        log.error("Exception", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiExceptionResponse.from("처리할 수 없는 예외가 발생했습니다."));
    }

    private int mappingSpringWebExceptionToStatusCode(final Exception ex) {
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
        }
        if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return HttpServletResponse.SC_NOT_ACCEPTABLE;
        }
        if (ex instanceof MissingPathVariableException) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        if (ex instanceof MissingServletRequestParameterException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (ex instanceof ServletRequestBindingException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (ex instanceof ConversionNotSupportedException) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        if (ex instanceof TypeMismatchException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (ex instanceof HttpMessageNotReadableException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (ex instanceof HttpMessageNotWritableException) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (ex instanceof MissingServletRequestPartException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (ex instanceof BindException) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        if (ex instanceof NoHandlerFoundException) {
            return HttpServletResponse.SC_NOT_FOUND;
        }
        if (ex instanceof AsyncRequestTimeoutException) {
            return HttpServletResponse.SC_SERVICE_UNAVAILABLE;
        }
        throw new IllegalStateException("지원하지 않는 예외 처리입니다.");
    }
}
