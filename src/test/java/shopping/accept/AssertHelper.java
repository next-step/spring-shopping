package shopping.accept;

import io.restassured.response.ValidatableResponse;
import java.util.Arrays;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;

class AssertHelper {

    static final class Product {

        static void assertAllProducts(final ValidatableResponse response,
                String... exactlyExpected) {
            response.statusCode(HttpStatus.OK.value());
            Arrays.stream(exactlyExpected)
                    .forEach(expected -> response.body(Matchers.containsString(expected)));
        }
    }
}
