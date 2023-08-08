package shopping.learn;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Disabled // application.yml ddl-auto : create로 바꿔야 아래 실행 가능
@Transactional
@SpringBootTest
class EntityMappingTest {

    @Autowired
    EntityManager entityManager;

    @Entity
    @Table(name = "member")
    class Member {

        @Id
        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name="team_id")
        private Team team;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }
    }

    @Entity
    @Table(name = "team")
    class Team {

        @Id
        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToMany(mappedBy = "team")
        private List<Member> members;

        public Team(List<Member> member) {
            this.members = member;
            member.forEach(m -> m.setTeam(this));
        }

        public Team() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<Member> getMembers() {
            return members;
        }

        public void setMembers(List<Member> members) {
            this.members = members;
        }
    }

    @DisplayName("양방향 매핑 시 OneToMany insert 문 확인")
    @Test
    void bidirectionalInsert() {
        Member member = new Member();
        Member member2 = new Member();
        Team team = new Team(List.of(member, member2));
        entityManager.persist(team);
        entityManager.flush();
        // there is no insert for member
    }
}
