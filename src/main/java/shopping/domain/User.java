package shopping.domain;

import java.util.Objects;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.LoginRequest;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

public class User {

    private final Long id;
    private final String email;
    private final String password;

    private User (final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static User from(final UserEntity userEntity) {
        return new User(
            userEntity.getId(),
            userEntity.getEmail(),
            userEntity.getPassword()
        );
    }

    public void matchPassword(final String password) {
        if (!Objects.equals(this.password, password)) {
            throw new ShoppingException(ErrorCode.INVALID_PASSWORD);
        }
    }

}
