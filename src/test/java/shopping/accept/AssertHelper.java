package shopping.accept;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.Arrays;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import shopping.domain.exception.StatusCodeException;

class AssertHelper {

    static final class Product {

        static void assertAllProducts(final ValidatableResponse response,
                String... exactlyExpected) {
            response.statusCode(HttpStatus.OK.value());
            Arrays.stream(exactlyExpected)
                    .forEach(expected -> response.body(Matchers.containsString(expected)));
        }
    }

    static final class Auth {

        static void assertJwt(final ExtractableResponse<Response> response) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.body()).isInstanceOf(String.class);
        }

        static void assertLoginFailed(final ExtractableResponse<Response> response) {
            StatusCodeException statusCodeException = response.as(StatusCodeException.class);
            
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(statusCodeException.getStatus()).isEqualTo("AUTH-401");
        }
    }
}
