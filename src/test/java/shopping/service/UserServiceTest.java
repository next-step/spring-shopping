package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.repository.UserRepository;
import shopping.utils.JwtProvider;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 단위 테스트")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("성공 : 로그인에 성공한다.")
    void successLogin() {
        /* given */
        final String userEmail = "test_email@woowafriends.com";
        final String userPassword = "test_password!";
        final String accessToken = "test_access_token";

        final LoginRequest loginRequest = new LoginRequest(userEmail, userPassword);
        final UserEntity userEntity = new UserEntity(1L, userEmail, userPassword);

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(userEntity));
        when(jwtProvider.getToken(userEmail)).thenReturn(accessToken);

        /* when */
        LoginResponse loginResponse = userService.login(loginRequest);

        /* then */
        verify(userRepository).findByEmail(userEmail);
        verify(jwtProvider).getToken(userEmail);
        assertThat(loginResponse.getAccessToken()).isEqualTo(accessToken);
    }

}
