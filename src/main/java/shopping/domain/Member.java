package shopping.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email"))
    private MemberEmail email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private MemberPassword password;

    protected Member() {
    }

    public Member(final MemberEmail email, final MemberPassword password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public MemberEmail getEmail() {
        return this.email;
    }

    public MemberPassword getPassword() {
        return this.password;
    }
}
