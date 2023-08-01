package shopping.domain.member;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Nickname nickname;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    protected Member() {
    }

    public Member(final Nickname nickname, final Email email, final Password password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void matchPassword(final Password requestPassword) {
        if (!this.password.isMatch(requestPassword)) {
            throw new ShoppingException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

    public Long getId() {
        return this.id;
    }

    public Nickname getNickname() {
        return this.nickname;
    }

    public Email getEmail() {
        return this.email;
    }

    public Password getPassword() {
        return this.password;
    }
}
