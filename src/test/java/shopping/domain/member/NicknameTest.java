package shopping.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class NicknameTest {

    @ParameterizedTest
    @ValueSource(strings = {"1234567890", "ABCDEFGHIJ"})
    @DisplayName("닉네임을 생성할 수 있다.")
    void createNickname(String value) {
        final Nickname nickname = Nickname.from(value);

        assertThat(nickname.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"12345678910", "ABCDEFGHIJKLMNOPQRSTU"})
    @DisplayName("빈 문자열이나 null , 10글자 초과의 입력으로 닉네임 생성할 때 예외를 던진다.")
    void validateNickname(String value) {
        assertThatThrownBy(() -> Nickname.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("닉네임은 1글자 이상 10글자이하 입니다.");
    }
}
