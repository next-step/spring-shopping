package shopping.auth.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "email"))
    private MemberEmail email;

    @Embedded
    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private MemberPassword password;

    protected Member() {
    }

    public Member(final MemberEmail email, final MemberPassword password) {
        this.email = email;
        this.password = password;
    }

    public boolean matchPassword(final String password) {
        return this.password.match(password);
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public String getPassword() {
        return this.password.getValue();
    }
}
