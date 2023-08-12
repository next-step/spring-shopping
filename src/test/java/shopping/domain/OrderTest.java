package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.exception.OrderException;

@DisplayName("Order 클래스는")
public class OrderTest {


    @Nested
    @DisplayName("new 생성자는")
    class Order_Constructor {

        @Test
        @DisplayName("Order 를 생성한다.")
        void createName() {
            // given
            Member member = new Member("home@naver.com", "1234");

            // when
            Exception exception = catchException(() -> new Order(member, 1300.0));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("Member 가 null 이면 OrderException 을 던진다.")
        void throwOrderException_whenMemberIsNull() {
            // given
            Member member = null;

            // when
            Exception exception = catchException(() -> new Order(member, 1300.0));

            // then
            assertThat(exception).isInstanceOf(OrderException.class);
            assertThat(exception.getMessage()).contains("member 가 존재하지 않습니다");
        }
    }

}
