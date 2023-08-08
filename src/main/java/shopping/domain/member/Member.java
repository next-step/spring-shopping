package shopping.domain.member;

import static shopping.exception.ShoppingErrorType.PASSWORD_NOT_MATCH;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import shopping.exception.ShoppingException;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "nickname", length = 10, nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", length = 40, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 18)
    private String password;

    protected Member() {
    }

    public Member(final Nickname nickname, final Email email, final Password password) {
        this.nickname = nickname.getValue();
        this.email = email.getValue();
        this.password = password.getValue();
    }

    public void matchPassword(final Password requestPassword) {
        if (!Password.from(this.password).isMatch(requestPassword)) {
            throw new ShoppingException(PASSWORD_NOT_MATCH);
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(nickname, member.nickname)
                && Objects.equals(email, member.email) && Objects.equals(password, member.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, password);
    }
}
