package shopping.exception;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class CommonExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);
    private static final String INTERNAL_SERVER_EXCEPTION_MESSAGE = "서버 내부에서 오류가 발생했습니다.";
    private static final String EXCHANGE_API_EXCEPTION_MESSAGE = "환율을 제공해주는 외부 서버에 문제가 발생했습니다.";

    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandleWithShoppingException(final ShoppingException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler({ResourceAccessException.class, HttpServerErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exceptionHandleWithExchangeAPIException(final RestClientException e) {
        LOGGER.error(e.getMessage());

        return ExceptionResponse.from(EXCHANGE_API_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exceptionHandleWithHttpClientErrorException(final HttpClientErrorException e) {
        LOGGER.error(e.getMessage());

        return ExceptionResponse.from(INTERNAL_SERVER_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exceptionHandleWithRestClientException(final RestClientException e) {
        LOGGER.error(e.getMessage());

        return ExceptionResponse.from(INTERNAL_SERVER_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exceptionHandleWithSQLException(final SQLException e) {
        LOGGER.error(e.getMessage());

        return ExceptionResponse.from(INTERNAL_SERVER_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exceptionHandleWithException(final Exception e) {
        LOGGER.error(e.getMessage());

        return ExceptionResponse.from(INTERNAL_SERVER_EXCEPTION_MESSAGE);
    }
}
