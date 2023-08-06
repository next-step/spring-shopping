package shopping.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements ExceptionType {

    NO_CONTENT_EMAIL(BAD_REQUEST, "이메일은 비어있거나 공백이면 안됩니다."),
    WRONG_EMAIL_FORMAT(BAD_REQUEST, "이메일이 형식에 맞지 않습니다."),
    NOT_FOUND_EMAIL(NOT_FOUND, "존재하지 않는 이메일입니다."),
    INVALID_PASSWORD_LENGTH(BAD_REQUEST, "비밀번호는 30자보다 길 수 없습니다."),
    NO_CONTENT_PASSWORD(BAD_REQUEST, "비밀번호는 비어있거나 공백이면 안됩니다."),
    NOT_MATCH_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    MemberExceptionType(final HttpStatus httpStatus, final String message) {
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
