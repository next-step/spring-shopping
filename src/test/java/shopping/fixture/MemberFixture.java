package shopping.fixture;

import org.springframework.test.util.ReflectionTestUtils;
import shopping.domain.member.Email;
import shopping.domain.member.Member;
import shopping.domain.member.Nickname;
import shopping.domain.member.Password;

public class MemberFixture {

    public static final Long MEMBER_ID = 1L;
    public static final Nickname NICKNAME = Nickname.from("nickname");
    public static final Email EMAIL = Email.from("proto_seo@naver.com");
    public static final Password PASSWORD = Password.from("123!@#asd");

    public static Member createMember(final Long id) {
        final Member member = new Member(NICKNAME, EMAIL, PASSWORD);
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }

}
