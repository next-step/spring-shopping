package shopping.service;

import org.springframework.stereotype.Service;
import shopping.domain.Member;
import shopping.domain.MemberEmail;
import shopping.domain.MemberPassword;
import shopping.dto.request.LoginRequest;
import shopping.exception.ShoppingException;
import shopping.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member matchMember(final LoginRequest loginRequest) {
        final MemberEmail email = new MemberEmail(loginRequest.getEmail());
        final MemberPassword password = new MemberPassword(loginRequest.getPassword());

        final Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new ShoppingException("존재하지 않는 이메일입니다. 입력값: " + email.getValue()));

        if (!member.getPassword().equals(password)) {
            throw new ShoppingException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}
