package shopping.dto.auth;

public class LoginMember {

    private final Long memberId;

    public LoginMember(final Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return this.memberId;
    }
}
