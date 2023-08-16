package shopping.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.domain.Member;
import shopping.auth.domain.MemberEmail;
import shopping.auth.dto.request.LoginRequest;
import shopping.global.exception.ShoppingException;
import shopping.auth.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Member matchMember(final LoginRequest loginRequest) {
        final String email = loginRequest.getEmail();
        final Member member = memberRepository.findByEmail(new MemberEmail(email))
            .orElseThrow(() -> new ShoppingException("존재하지 않는 이메일입니다. 입력값: " + email));

        final String password = loginRequest.getPassword();
        if (!member.matchPassword(password)) {
            throw new ShoppingException("비밀번호가 일치하지 않습니다. 입력값: " + password);
        }

        return member;
    }
}
