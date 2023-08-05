package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.auth.PasswordEncoder;
import shopping.auth.TokenExtractor;
import shopping.auth.TokenProvider;
import shopping.domain.user.User;
import shopping.dto.request.LoginRequest;
import shopping.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@DisplayName("인증 인수 테스트")
class AuthIntegrationTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        String password = "1234";
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.save(new User("test@example.com", encodedPassword));
    }

    @DisplayName("로그인 페이지 연동")
    @Test
    void loginPage() {
        // when, then
        RestAssured
                .given().log().all()
                .when().get("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("정상 로그인 요청 성공시 200 Ok")
    @Test
    void whenLoginSuccessThenGetToken() {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("이메일이 null일시 400 BadRequest 반환")
    @Test
    void nullEmail() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", null);
        loginRequest.put("password", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호가 null일시 400 BadRequest 반환")
    @Test
    void nullPassword() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@example.com");
        loginRequest.put("password", null);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일이 빈 문자열 일시 400 BadRequest 반환")
    @Test
    void blankEmail() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "   ");
        loginRequest.put("password", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호가 빈 문자열 일시 400 BadRequest 반환")
    @Test
    void blankPassword() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@example.com");
        loginRequest.put("password", "    ");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("이메일이 100자 이상일시 400 BadRequest 반환")
    @Test
    void longEmail() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@example.com".repeat(100));
        loginRequest.put("password", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호가 100자 이상일시 400 BadRequest 반환")
    @Test
    void longPassword() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@example.com");
        loginRequest.put("password", "1234".repeat(100));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("등록되지 않은 이메일일시 400 BadRequest 반환")
    @Test
    void notRegisteredEmail() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "wrongEmail@example.com");
        loginRequest.put("password", "1234");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("비밀번호를 틀렸을 시 400 BadRequest 반환")
    @Test
    void wrongPassword() {
        // given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "test@example.com");
        loginRequest.put("password", "wrongPassword");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/login/token")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("액세스 토큰이 없으면 로그인 페이지로 리다이렉트")
    @Test
    void redirectIfNoAccessToken() {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .body(
                        containsString("login")
                );
    }

    @DisplayName("비정상 액세스 토큰으로 접근시 400 BadRequest 반환")
    @Test
    void invalidAccessToken() {
        // given
        Long invalidId = 100L;
        String email = "notexist@example.com";
        String digest = passwordEncoder.encode("1234");
        User user = new User(invalidId, email, digest);
        String accessToken = tokenProvider.issueToken(user);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
