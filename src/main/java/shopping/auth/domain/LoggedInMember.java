package shopping.auth.domain;

public class LoggedInMember {

    private Long id;

    public LoggedInMember(Long id) {
        this.id = id;
    }

    public LoggedInMember(String id) {
        this(Long.parseLong(id));
    }

    public Long getId() {
        return id;
    }
}
