package shopping.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

public enum AuthExceptionType implements ExceptionType {

    NO_AUTHORIZATION_HEADER(UNAUTHORIZED, "Authorization 헤더가 존재하지 않습니다."),
    NO_CONTENT_TOKEN(UNAUTHORIZED, "토큰 값이 존재하지 않습니다."),
    NOT_BEARER_TOKEN(UNAUTHORIZED, "토큰이 Bearer로 시작하지 않습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    AuthExceptionType(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
